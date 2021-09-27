package vboled.netcracker.musicstreamer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "music-streamer")
@ConstructorBinding
public class ApplicationConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    private final SecurityConfiguration securityConfiguration;
    private final FileConfiguration fileConfiguration;

    public ApplicationConfiguration(SecurityConfiguration security, FileConfiguration file) {
        this.securityConfiguration = security;
        this.fileConfiguration = file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public SecurityConfiguration getSecurityConfiguration() {
        return securityConfiguration;
    }

    public static final class FileConfiguration {
        private final String uploadPath;
        private final ImageConfiguration imageConfiguration;
        private final AudioConfiguration audioConfiguration;

        public FileConfiguration(String uploadPath, ImageConfiguration image, AudioConfiguration audio) {
            this.uploadPath = uploadPath;
            this.imageConfiguration = image;
            this.audioConfiguration = audio;
        }

        public AudioConfiguration getAudioConfiguration() {
            return audioConfiguration;
        }

        public ImageConfiguration getImageConfiguration() {
            return imageConfiguration;
        }

        public String getUploadPath() {
            return uploadPath;
        }

        public static final class AudioConfiguration {
            private final String dir;
            private final Long maxSize;
            private final Set<String> ext;

            public String getDir() {
                return dir;
            }

            public Long getMaxSize() {
                return maxSize;
            }

            public Set<String> getExt() {return ext;}

            public AudioConfiguration(String dir, Long maxSize, String ext) {
                this.dir = dir;
                this.maxSize = maxSize;
                this.ext = new HashSet<>();
                Arrays.stream(ext.split(" ")).forEach(word -> this.ext.add("." + word.trim()));
            }
        }

        public static final class ImageConfiguration {
            private final String dir;
            private final Long maxSize;
            private final Set<String> ext;

            public String getDir() {
                return dir;
            }

            public Long getMaxSize() {
                return maxSize;
            }

            public Set<String> getExt() {return ext;}

            public ImageConfiguration(String dir, Long maxSize, String ext) {
                this.dir = dir;
                this.maxSize = maxSize;
                this.ext = new HashSet<>();
                Arrays.stream(ext.split(" ")).forEach(word -> this.ext.add("." + word.trim()));
            }
        }
    }

    public static final class SecurityConfiguration {
        private final JwtConfiguration jwtConfiguration;

        public SecurityConfiguration(JwtConfiguration jwt) {
            this.jwtConfiguration = jwt;
        }

        public JwtConfiguration getJwtConfiguration() {
            return jwtConfiguration;
        }

        public static final class JwtConfiguration {
            private final String secret;
            private final String type;
            private final String issuer;
            private final String audience;
            private final String header;
            private final String path;
            private final Long expireTimeSeconds;

            public JwtConfiguration(String secret, String type, String issuer, String audience, String header,
                                    Long expireTimeSeconds, String path) {
                this.secret = secret;
                this.type = type;
                this.issuer = issuer;
                this.audience = audience;
                this.path = path;
                this.header = header;
                this.expireTimeSeconds = expireTimeSeconds;
            }

            public String getSecret() {
                return secret;
            }

            public String getType() {
                return type;
            }

            public String getIssuer() {
                return issuer;
            }

            public String getAudience() {
                return audience;
            }

            public Long getExpireTimeSeconds() {
                return expireTimeSeconds;
            }

            public String getHeader() {
                return header;
            }

            public String getPath() {return path;}
        }
    }

    @Override
    public String toString() {
        return "ApplicationConfiguration{" +
                "securityConfiguration=" + securityConfiguration +
                '}';
    }

    @PostConstruct
    public void postInit(){
        logger.info(this.toString());
    }
}
