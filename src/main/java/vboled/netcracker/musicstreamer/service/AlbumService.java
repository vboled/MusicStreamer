package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.exceptions.AlbumCreationFailed;
import vboled.netcracker.musicstreamer.exceptions.AlbumNotFoundException;
import vboled.netcracker.musicstreamer.model.Album;

import java.util.List;
import java.util.NoSuchElementException;

public interface AlbumService {

    Album getById(Long id) throws AlbumNotFoundException;

    void create(Album album) throws AlbumCreationFailed;

    List<Album> readAll();

    List<Album> search(String search);

    Album partialUpdateAlbum(Album update) throws AlbumNotFoundException;

    Album fullUpdateAlbum(Album update) throws AlbumNotFoundException;

    void delete(Long id) throws AlbumNotFoundException;

    Album setCover(Long id, String uuid);
}
