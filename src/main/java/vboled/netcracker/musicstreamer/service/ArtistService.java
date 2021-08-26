package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Artist;

import java.util.List;
import java.util.NoSuchElementException;

public interface ArtistService {

    Artist getById(Long id) throws NoSuchElementException;

    void create(Artist artist) throws IllegalArgumentException;

    List<Artist> readAll();

    Artist partialUpdateArtist(Artist update) throws NoSuchElementException;

    Artist fullUpdateArtist(Artist update) throws NoSuchElementException;

    void delete(Artist artist);

    List<Artist> search(String search);

    Artist setCover(Long id, String name);

    List<Artist> getArtistsByOwnerId(Long id);
}
