package vboled.netcracker.musicstreamer.model.validator;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AudioValidator implements FileValidator {

    private final Set<String> audioExt = new HashSet<>(Arrays.asList(".mp3",".ogg", ".wav"));

    @Value("${audio.storage.dir}")
    private String audioDir= "audio";

    @Value("${file.storage.path}")
    private String uploadPath = "src/main/frontend/public";

    @Override
    public String getPath() {
        return uploadPath + "/" + audioDir;
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
        return audioExt;
    }
}
