package vboled.netcracker.musicstreamer.model.validator;

import java.util.Set;

public interface FileValidator {

    String getPath();

    String getContentType(String uuid) throws IllegalArgumentException;

    Set<String> getExtensions();

}
