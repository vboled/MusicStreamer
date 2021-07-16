package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Genre;

public interface GenreService {

    boolean existByName(String name);

    Genre getById(int id);

    boolean updateName(int id, String name);

    boolean create(Genre genre);

    boolean existById(int id);
}
