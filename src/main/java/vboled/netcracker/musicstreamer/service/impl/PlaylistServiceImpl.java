package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.repository.PlaylistRepository;
import vboled.netcracker.musicstreamer.service.PlaylistService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist getById(Long id) throws NoSuchElementException {
        return playlistRepository.findById(id).get();
    }

    @Override
    public void create(Playlist playlist) {
        try {
            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Playlist> readAll() {
        return playlistRepository.findAll();
    }

    private Playlist updateCommonFields(Playlist update) throws NoSuchElementException {
        Playlist toUpdate = getById(update.getId());
        if (update.getName() != null) {
            toUpdate.setName(update.getName());
        }
        return toUpdate;
    }

    @Override
    public Playlist partialUpdatePlaylist(Playlist update) throws NoSuchElementException {
        Playlist updated = updateCommonFields(update);
        playlistRepository.save(updated);
        return updated;
    }

    @Override
    public Playlist fullUpdatePlaylist(Playlist update) throws NoSuchElementException {
        Playlist toUpdate = updateCommonFields(update);
        if (update.getOwnerID() != null) {
            // Add validation
            toUpdate.setOwnerID(update.getOwnerID());
        }
        playlistRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void delete(Long id) {
        if (!playlistRepository.existsById(id))
            throw new NoSuchElementException();
        playlistRepository.deleteById(id);
    }

}
