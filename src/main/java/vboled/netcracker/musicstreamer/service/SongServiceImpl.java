package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.SongRepository;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void create(Song song) {
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

    @Override
    public boolean updateUuid(String oldUuid, String newUuid) {
        if (songRepository.existsByUuid(oldUuid)) {
            Song song = songRepository.findByUuid(oldUuid);
            song.setUuid(newUuid);
            songRepository.save(song);
            return true;
        }
        return false;
    }
}
