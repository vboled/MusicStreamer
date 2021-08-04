package vboled.netcracker.musicstreamer.service;

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

    void delete(Long id) throws NoSuchElementException;

    List<Artist> search(String search);
}
