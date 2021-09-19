package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.repository.AddedSongRepository;
import vboled.netcracker.musicstreamer.repository.PlaylistRepository;
import vboled.netcracker.musicstreamer.service.PlaylistService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final AddedSongRepository addedSongRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, AddedSongRepository addedSongRepository) {
        this.playlistRepository = playlistRepository;
        this.addedSongRepository = addedSongRepository;
    }

    @Override
    public Playlist getById(Long id) throws NoSuchElementException {
        return playlistRepository.findById(id).get();
    }

    @Override
    public Playlist create(Playlist playlist) {
        return playlistRepository.save(playlist);
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
        if (update.getDescription() != null) {
            toUpdate.setDescription(update.getDescription());
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
    public void delete(Playlist playlist) {
        addedSongRepository.deleteByPlaylist(playlist);
        playlistRepository.deleteById(playlist.getId());
    }

    @Override
    public Playlist createMainPlaylist(Long ownerId) {
        Playlist res = new Playlist();
        res.setMain(true);
        res.setOwnerID(ownerId);
        res.setName("Favourite song");
        res.setDescription("Songs marked \"liked\" will be stored here");
        playlistRepository.save(res);
        return res;
    }

    @Override
    public Playlist setCover(Long id, String uuid) {
        Playlist playlist = getById(id);
        playlist.setUuid(uuid);
        playlistRepository.save(playlist);
        return playlist;
    }

    @Override
    public Playlist getMainPlaylistByUserId(Long id) {
        return playlistRepository.getMainPlaylist(id);
    }

}
