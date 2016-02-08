package racejournal.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import racejournal.data.RaceDao;
import racejournal.data.RaceMongoRawDao;
import racejournal.domain.Race;

import javax.sql.DataSource;

/**
 * Created by alaplante on 2/4/16.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class DataConfig {
    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .generateUniqueName(true)
            .setType(EmbeddedDatabaseType.H2)
            .setScriptEncoding("UTF-8")
            .ignoreFailedDrops(true)
            .addScript("schema.sql")
//            .addScript("data.sql")
            .build();
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        localSessionFactoryBuilder.addAnnotatedClasses(Race.class);
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public RaceDao raceDao() {
        RaceMongoRawDao raceMongoDao = new RaceMongoRawDao();
        raceMongoDao.setDbUri(environment.getProperty("mongouri"));
        raceMongoDao.setDbName(environment.getProperty("mongodb"));
        raceMongoDao.init();
        return raceMongoDao;
    }
}
