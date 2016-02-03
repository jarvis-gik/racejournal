package belllap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import belllap.service.RaceService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by alaplante on 2/2/16.
 */
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = "belllap")
public class Config {
    @Autowired
    Environment environment;

    @Bean
    public RaceService raceService() {
        RaceService raceService = new RaceService();
        raceService.setBootstrapFile(environment.getProperty("bootstrapfile"));
        raceService.setProtoBufFile(environment.getProperty("protobuffile"));
        return raceService;
    }
}
