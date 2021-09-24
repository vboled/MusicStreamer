package vboled.netcracker.musicstreamer.dto;

public class AuthDTO {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthDTO setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
