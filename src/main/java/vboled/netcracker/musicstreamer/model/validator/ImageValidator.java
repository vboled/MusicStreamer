package vboled.netcracker.musicstreamer.model.validator;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ImageValidator implements FileValidator {

    private final Set<String> imageExt = new HashSet<>(Arrays.asList(".jpeg",".jpg", ".png", ".gif"));

    @Value("${image.storage.dir}")
    private String imageDir= "img";

    @Value("${file.storage.path}")
    private String uploadPath = "src/main/frontend/public";

    @Override
    public String getPath() {
        return uploadPath + "/" + imageDir;
    }

    @Override
    public String getContentType(String uuid) throws IllegalArgumentException {
        String ext = uuid.substring(uuid.lastIndexOf('.'));
        if (ext.equals(".jpeg") || ext.equals(".jpg"))
            return "image/jpeg";
        else if (ext.equals(".png"))
            return "image/png";
        else if (ext.equals(".gif"))
            return "image/gif";
        throw new IllegalArgumentException("Wrong file ext.");
    }

    @Override
    public Set<String> getExtensions() {
        return imageExt;
    }
}
