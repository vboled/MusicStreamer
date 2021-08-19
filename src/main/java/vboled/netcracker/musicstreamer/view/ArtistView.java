package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

@Data
public class ArtistView {

    private Artist artist;
    private List<Album> albums;
    private List<Song> songs;

    public ArtistView(Artist artist, List<Album> albums, List<Song> songs) {
        this.artist = artist;
        this.albums = albums;
        this.songs = songs;
    }
}
