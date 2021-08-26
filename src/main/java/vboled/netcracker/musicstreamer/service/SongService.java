package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;
import java.util.NoSuchElementException;

public interface SongService {

    void create(Song song);

    void delete(Song song);

    List<Song> readAll();

    Song read(String uuid);

    Song partialUpdateSong(Song update);

    Song fullUpdateSong(Song update);

    List<Song> search(String search);

    Song getById(Long id);

    Song setSongFile(Long id, String uuid);

    List<Song> getByArtist(Artist artist);

    void deleteAudio(Song song);

    List<Song> getByAlbum(Album album);

    void deleteByAlbum(Album album);
}
