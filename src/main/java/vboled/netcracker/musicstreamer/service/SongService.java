package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

public interface SongService {

    void create(Song song);

    boolean delete(String uuid);

    List<Song> readAll();

    Song read(String uuid);

    Song updateSong(Song update);
}
