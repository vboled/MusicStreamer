package vboled.netcracker.musicstreamer.model.validator;

import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ImageValidator implements FileValidator {

    private final Set<String> imageExt = new HashSet<>(Arrays.asList(".jpeg",".jpg", ".png", ".gif"));

    private final ApplicationConfiguration.FileConfiguration fileConfiguration;

    public ImageValidator(ApplicationConfiguration.FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    @Override
    public String getPath() {
        return fileConfiguration.getUploadPath() + "/" +
                fileConfiguration.getImageConfiguration().getDir();
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

    @Override
    public Long getMaxSize() {
        return fileConfiguration.getImageConfiguration().getMaxSize();
    }
}
