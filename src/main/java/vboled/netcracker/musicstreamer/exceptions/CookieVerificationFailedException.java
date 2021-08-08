package vboled.netcracker.musicstreamer.exceptions;

public class CookieVerificationFailedException extends RuntimeException {
    public CookieVerificationFailedException(String message) {
        super(message);
    }
}
