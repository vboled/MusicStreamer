package vboled.netcracker.musicstreamer.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.SongRepository;
import vboled.netcracker.musicstreamer.service.SongService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void create(Song song) throws IllegalArgumentException {
        try {
            songRepository.save(song);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public boolean delete(String uuid) {
        if (songRepository.existsByUuid(uuid)) {
            songRepository.deleteByUuid(uuid);
            return true;
        }
        return false;
    }

    @Override
    public List<Song> readAll() {
        return songRepository.findAll();
    }

    private Song updateCommonFields(Song update) {
//        if (!songRepository.existsByUuid(update.getUuid()))
//            throw new NoSuchElementException("No song with such id");
        Song songToUpdate = songRepository.findByUuid(update.getUuid()).get();
        if (update.getWords() != null) {
            songToUpdate.setWords(update.getWords());
        }
        if (update.getAuthor() != null) {
            songToUpdate.setAuthor(update.getAuthor());
        }
        if (update.getVolume() != null) {
            songToUpdate.setVolume(update.getVolume());
        }
        if (update.getReleaseDate() != null) {
            songToUpdate.setReleaseDate(update.getReleaseDate());
        }
        if (update.getAlbum() != null) {
            // Add validation
            songToUpdate.setAlbum(update.getAlbum());
        }
        if (update.getArtist() != null) {
            // Add validation
            songToUpdate.setArtist(update.getArtist());
        }
        if (update.getTitle() != null) {
            // Add validation
            songToUpdate.setTitle(update.getTitle());
        }
        return songToUpdate;
    }

    // update fields enable for owner
    @Override
    public Song partialUpdateSong(Song update) {
        Song updated = updateCommonFields(update);
        songRepository.save(updated);
        return updated;
    }

    // update all fields (for admin)
    @Override
    public Song fullUpdateSong(Song update) {
        Song toUpdate = updateCommonFields(update);
        if (update.getOwnerID() != null) {
            // Add validation
            toUpdate.setOwnerID(update.getOwnerID());
        }
        if (update.getDuration() != null) {
            toUpdate.setDuration(update.getDuration());
        }
        if (update.getCreateDate() != null) {
            toUpdate.setCreateDate(update.getCreateDate());
        }
        if (update.getEditDate() != null) {
            toUpdate.setEditDate(update.getEditDate());
        }
        songRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public Song read(String uuid) throws NoSuchElementException {
        return songRepository.findByUuid(uuid).get();
    }

    @Override
    public List<Song> search(String search) {
        return songRepository.findAllByTitleIsLike(search);
    }
}
