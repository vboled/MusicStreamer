package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Genre;

import java.util.List;

public interface GenreService {

    boolean existByName(String name);

    Genre getById(Long id);

    boolean updateName(Long id, String name);

    boolean create(Genre genre);

    boolean existById(Long id);

    List<Genre> readAll();

    List<Genre> search(String search);
}
