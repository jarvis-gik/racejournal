package racejournal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import racejournal.service.RaceResultsService;
import racejournal.service.RaceService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import racejournal.service.RiderService;
import racejournal.util.UsacResultsDownloadParser;

/**
 * Created by alaplante on 2/2/16.
 */
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = "racejournal")
public class AppConfig {
    @Autowired
    Environment environment;

    @Bean
    public RaceService raceService() {
        RaceService raceService = new RaceService();
        raceService.setBootstrapFile(environment.getProperty("bootstrapfile"));
//        raceService.setProtoBufFile(environment.getProperty("protobuffile"));
        return raceService;
    }

    @Bean
    public RaceResultsService raceResultsService() {
        return new RaceResultsService();
    }

    @Bean
    public RiderService riderService() {
        return new RiderService();
    }

    @Bean
    public UsacResultsDownloadParser usacResultsDownloadParser() {
        return new UsacResultsDownloadParser();
    }
}
