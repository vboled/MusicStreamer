package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Playlist;

import java.util.List;
import java.util.NoSuchElementException;

public interface PlaylistService {

    Playlist getById(Long id) throws NoSuchElementException;

    void create(Playlist playlist) throws IllegalArgumentException;

    List<Playlist> readAll();

    Playlist partialUpdatePlaylist(Playlist update) throws NoSuchElementException;

    Playlist fullUpdatePlaylist(Playlist update) throws NoSuchElementException;

    void delete(Long id) throws NoSuchElementException;

    static Playlist createMainPlaylist(Long ownerId) {
        Playlist res = new Playlist();
        res.setMain(true);
        res.setOwnerID(ownerId);
        res.setName("Favourite song");
        res.setDescription("Songs marked \"liked\" will be stored here");
        return res;
    }
}
