package vboled.netcracker.musicstreamer.dto;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

@Data
public class UserView {
    private User user;
    private List<Playlist> playlistLists;

    public UserView(User user, List<Playlist> allPlaylists) {
        this.user = user;
        this.playlistLists = allPlaylists;
    }
}
