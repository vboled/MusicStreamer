package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

public interface AddedSongService {

    List<AddedSong> getAllByPlaylist(Playlist playlist);

    AddedSong addSongToPlaylist(Playlist playlist, Song song);

    void deleteSong(AddedSong addedSong);

    AddedSong getById(Long addedSongID);

    void deleteBySong(Song song);
}
