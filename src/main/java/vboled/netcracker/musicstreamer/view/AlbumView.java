package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

@Data
public class AlbumView {

    private Album album;
    private List<SongView> songs;

    public AlbumView(Album album, List<SongView> songs) {
        this.album = album;
        this.songs = songs;
    }
}
