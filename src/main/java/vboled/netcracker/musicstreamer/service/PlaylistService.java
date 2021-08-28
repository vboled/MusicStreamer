package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Playlist;

import java.util.List;
import java.util.NoSuchElementException;

public interface PlaylistService {

    Playlist getById(Long id) throws NoSuchElementException;

    Playlist create(Playlist playlist) throws IllegalArgumentException;

    List<Playlist> readAll();

    Playlist partialUpdatePlaylist(Playlist update) throws NoSuchElementException;

    Playlist fullUpdatePlaylist(Playlist update) throws NoSuchElementException;

    void delete(Playlist playlist);

    Playlist createMainPlaylist(Long ownerId);

    Playlist setCover(Long id, String uuid);

    Playlist getMainPlaylistByUserId(Long id);
}
