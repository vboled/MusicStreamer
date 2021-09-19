package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;

import java.util.List;

@Data
public class PlaylistView {

    private Playlist playlist;
    private List<AddedSong> songs;

    public PlaylistView(Playlist playlist, List<AddedSong> songs) {
        this.playlist = playlist;
        this.songs = songs;
    }
}
