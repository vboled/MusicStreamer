package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

@Data
public class UserView {
    private User user;
    private List<Playlist> playlistLists;
    private Listening listening;

    public UserView(User user, List<Playlist> allPlaylists, Listening listening) {
        this.user = user;
        this.listening = listening;
        this.playlistLists = allPlaylists;
    }
}
