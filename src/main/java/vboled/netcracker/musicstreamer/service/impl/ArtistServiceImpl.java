package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.repository.AlbumRepository;
import vboled.netcracker.musicstreamer.repository.ArtistRepository;
import vboled.netcracker.musicstreamer.service.AlbumService;
import vboled.netcracker.musicstreamer.service.ArtistService;

import javax.security.auth.DestroyFailedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumService albumService;

    public ArtistServiceImpl(ArtistRepository artistRepository, AlbumService albumService) {
        this.artistRepository = artistRepository;
        this.albumService = albumService;
    }

    @Override
    public Artist getById(Long id) throws NoSuchElementException {
        return artistRepository.findById(id).get();
    }

    @Override
    public void create(Artist artist) {
        try {
            artistRepository.save(artist);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Artist> readAll() {
        return artistRepository.findAll();
    }

    private Artist updateCommonFields(Artist update) throws NoSuchElementException {
        Artist toUpdate = getById(update.getId());
        if (update.getName() != null) {
            toUpdate.setName(update.getName());
        }
        return toUpdate;
    }

    @Override
    public Artist partialUpdateArtist(Artist update) throws NoSuchElementException {
        Artist updated = updateCommonFields(update);
        artistRepository.save(updated);
        return updated;
    }

    @Override
    public Artist fullUpdateArtist(Artist update) throws NoSuchElementException {
        Artist toUpdate = updateCommonFields(update);
        if (update.getCreateDate() != null) {
            toUpdate.setCreateDate(update.getCreateDate());
        }
        if (update.getEditDate() != null) {
            toUpdate.setEditDate(update.getEditDate());
        }
        if (update.getOwnerID() != null) {
            // Add validation
            toUpdate.setOwnerID(update.getOwnerID());
        }
        artistRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void delete(Artist artist) {
        try {
            albumService.deleteByArtist(artist);
        } catch (DestroyFailedException e) {
            e.printStackTrace();
        }
        artistRepository.deleteById(artist.getId());
    }

    @Override
    public List<Artist> search(String search) {
        return artistRepository.searchByName(search);
    }

    @Override
    public Artist setCover(Long id, String name) {
        Artist artist = getById(id);
        artist.setUuid(name);
        artistRepository.save(artist);
        System.out.println(artist);
        return artist;
    }

    @Override
    public List<Artist> getArtistsByOwnerId(Long id) {
        return artistRepository.findAllByOwnerID(id);
    }
}
