package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

@Data
public class ContentView {

    private User owner;

    private List<Album> albums;

    private List<Artist> artists;

    public ContentView(User owner, List<Album> albums, List<Artist> artists) {
        this.owner = owner;
        this.albums = albums;
        this.artists = artists;
    }
}
