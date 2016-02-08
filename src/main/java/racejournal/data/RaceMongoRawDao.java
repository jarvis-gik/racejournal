package racejournal.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import racejournal.domain.Race;
import racejournal.domain.RaceType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 *
 * No Spring or abstraction - just raw (kind of ugly) Mongo driver code
 */
@Repository
public class RaceMongoRawDao implements RaceDao {
    private final static Logger logger = LoggerFactory.getLogger(RaceMongoRawDao.class);

    private String dbUri;
    private String dbName;
//    private MongoClientURI mongoClientURI;
//    private MongoClient mongoClient;
    MongoDatabase mongoDatabase;


    public void init() {
        MongoClientURI mongoClientURI = new MongoClientURI(dbUri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    @Override
    public List<Race> fetchRaces() {
        List <Race> races = new ArrayList<Race>();
        logger.info("Mongo fetchRaces");
        MongoCollection<Document> collection = mongoDatabase.getCollection("races");
        MongoCursor<Document> mongoCursor = collection.find().iterator();
        try {
            while(mongoCursor.hasNext()) {
                Race race = new Race();
//                logger.info("Found in mongo {}", mongoCursor.next().toJson());
                Document document = mongoCursor.next();
                race.setId(document.getLong("id"));
                race.setName(document.getString("name"));
                race.setDate(convertDate(document.getDate("date")));
                race.setCity(document.getString("city"));
                race.setState(document.getString("state"));
                race.setRaceType(RaceType.valueOf(document.getString("category")));
                races.add(race);
            }
        } finally {
            mongoCursor.close();
        }
        return races;
    }

    @Override
    public void saveRaces(List<Race> races) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("races");
        for(Race race : races) {
            logger.info("Save race {} as document to MonggoDB", race.getName());
            Document document = new Document("_class", "racejournal.domain.Race")
                .append("id", race.getId())
                .append("name", race.getName())
                .append("date", convertLocalDate(race.getDate()))
                .append("city", race.getCity())
                .append("state", race.getState())
                .append("category", race.getRaceType().name());
            collection.insertOne(document);
        }
    }

    private Date convertLocalDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private LocalDate convertDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public void setDbUri(String dbUri) {
        logger.info("Mongo DB URI {}", dbUri);
        this.dbUri = dbUri;
    }

    public void setDbName(String dbName) {
        logger.info("Mongo DB name {}", dbName);
        this.dbName = dbName;
    }
}
