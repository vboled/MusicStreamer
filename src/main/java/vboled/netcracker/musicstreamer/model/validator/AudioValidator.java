package vboled.netcracker.musicstreamer.model.validator;

import org.springframework.beans.factory.annotation.Value;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AudioValidator implements FileValidator {

    private final ApplicationConfiguration.FileConfiguration fileConfiguration;

    public AudioValidator(ApplicationConfiguration.FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    @Override
    public String getPath() {
        return fileConfiguration.getUploadPath() + "/" +
                fileConfiguration.getAudioConfiguration().getDir();
    }

    @Override
    public String getContentType(String uuid) throws IllegalArgumentException {
        String ext = uuid.substring(uuid.lastIndexOf('.'));
        if (ext.equals(".mp3"))
            return "audio/mpeg";
        else if (ext.equals(".wav"))
            return "audio/wav";
        else if (ext.equals(".ogg"))
            return "audio/ogg";
        throw new IllegalArgumentException("Wrong file ext.");
    }

    @Override
    public Set<String> getExtensions() {
        return fileConfiguration.getAudioConfiguration().getExt();
    }

    @Override
    public Long getMaxSize() {
        return fileConfiguration.getAudioConfiguration().getMaxSize();
    }
}
