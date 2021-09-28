package vboled.netcracker.musicstreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
@EnableCaching
public class MusicstreamerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicstreamerApplication.class, args);
    }

}
