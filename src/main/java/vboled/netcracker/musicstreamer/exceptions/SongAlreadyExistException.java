package vboled.netcracker.musicstreamer.exceptions;

public class SongAlreadyExistException extends RuntimeException {
    public SongAlreadyExistException(String message) {
        super(message);
    }
}
