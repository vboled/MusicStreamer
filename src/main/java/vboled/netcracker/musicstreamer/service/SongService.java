package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;
import java.util.NoSuchElementException;

public interface SongService {

    void create(Song song);

    void delete(Long id) throws NoSuchElementException;

    List<Song> readAll();

    Song read(String uuid);

    Song partialUpdateSong(Song update);

    Song fullUpdateSong(Song update);

    List<Song> search(String search);

    Song getById(Long id);

    Song setSongFile(Long id, String uuid);

    List<Song> getByArtistId(Long artistId);

    List<Song> getByAlbumId(Long albumId);

    void deleteAudio(Long id);
}
