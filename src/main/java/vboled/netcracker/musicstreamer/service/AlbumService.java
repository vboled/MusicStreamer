package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Album;

import java.util.List;
import java.util.NoSuchElementException;

public interface AlbumService {

    Album getById(Long id) throws NoSuchElementException;

    void create(Album album) throws IllegalArgumentException;

    List<Album> readAll();

    List<Album> search(String search);

    Album partialUpdateAlbum(Album update) throws NoSuchElementException;

    Album fullUpdateAlbum(Album update) throws NoSuchElementException;

    void delete(Long id) throws NoSuchElementException;

    Album setCover(Long id, String uuid);

    void deleteCover(Long id);
}
