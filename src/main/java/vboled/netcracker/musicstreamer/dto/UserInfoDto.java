package vboled.netcracker.musicstreamer.dto;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

@Data
public class UserInfoDto {
    private User user;
    private List<Playlist> playlistLists;

    public UserInfoDto(User user, List<Playlist> allPlaylists) {
        this.user = user;
        this.playlistLists = allPlaylists;
    }
}
