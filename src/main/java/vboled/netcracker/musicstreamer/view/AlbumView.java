package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Genre;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

@Data
public class AlbumView {

    private Album album;
    private List<SongView> songs;
    private List<Genre> genres;

    public AlbumView(Album album, List<SongView> songViews, List<Genre> genres) {
        this.album = album;
        this.songs = songViews;
        this.genres = genres;
    }
}
