package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;

public interface GenreService {

    boolean existByName(String name);

    Genre getById(Long id) throws NoSuchElementException;

    boolean updateName(Long id, String name);

    boolean create(Genre genre);

    List<Genre> readAll();

    List<Genre> search(String search);
}
