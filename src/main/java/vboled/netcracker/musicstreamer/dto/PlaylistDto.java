package vboled.netcracker.musicstreamer.dto;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;

import java.util.List;

@Data
public class PlaylistDto {

    private Playlist playlist;
    private List<AddedSong> songs;

    public PlaylistDto(Playlist playlist, List<AddedSong> songs) {
        this.playlist = playlist;
        this.songs = songs;
    }
}
