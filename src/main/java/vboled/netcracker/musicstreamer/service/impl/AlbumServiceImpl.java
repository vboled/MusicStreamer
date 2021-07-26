package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.repository.AlbumRepository;
import vboled.netcracker.musicstreamer.service.AlbumService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album getById(Long id) throws NoSuchElementException {
        return albumRepository.findById(id).get();
    }

    @Override
    public void create(Album album) {
        try {
            albumRepository.save(album);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Album> readAll() {
        return albumRepository.findAll();
    }

    @Override
    public List<Album> search(String search) {
        return null;
    }

    private Album updateCommonFields(Album update) throws NoSuchElementException {
        Album toUpdate = getById(update.getId());
        if (update.getReleaseDate() != null)
            toUpdate.setReleaseDate(update.getReleaseDate());
        if (update.getGenre() != null) {
            toUpdate.setGenre(update.getGenre());
        }
        if (update.getName() != null) {
            toUpdate.setName(update.getName());
        }
        if (update.getVolumes() != null) {
            toUpdate.setVolumes(update.getVolumes());
        }
        if (update.getType() != null) {
            toUpdate.setType(update.getType());
        }
        return toUpdate;
    }

    @Override
    public Album partialUpdateAlbum(Album update) throws NoSuchElementException {
        Album updated = updateCommonFields(update);
        albumRepository.save(updated);
        return updated;
    }

    @Override
    public Album fullUpdateAlbum(Album update) throws NoSuchElementException {
        Album toUpdate = updateCommonFields(update);
        if (update.getOwnerID() != null) {
            // Add validation
            toUpdate.setOwnerID(update.getOwnerID());
        }
        if (update.getCreateDate() != null) {
            toUpdate.setCreateDate(update.getCreateDate());
        }
        if (update.getEditDate() != null) {
            toUpdate.setEditDate(update.getEditDate());
        }
        albumRepository.save(toUpdate);
        return toUpdate;
    }
}
