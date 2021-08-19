package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;

import java.util.List;

@Data
public class ContentView {

    private List<Album> albums;

    private List<Artist> artists;

    public ContentView(List<Album> albums, List<Artist> artists) {
        this.albums = albums;
        this.artists = artists;
    }
}
