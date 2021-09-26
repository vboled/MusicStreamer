package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.exceptions.AlbumCreationFailed;
import vboled.netcracker.musicstreamer.exceptions.AlbumNotFoundException;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.model.validator.ImageValidator;
import vboled.netcracker.musicstreamer.repository.AlbumRepository;
import vboled.netcracker.musicstreamer.service.AlbumService;
import vboled.netcracker.musicstreamer.service.FileService;
import vboled.netcracker.musicstreamer.service.SongService;

import javax.security.auth.DestroyFailedException;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationConfiguration.FileConfiguration fileConfiguration;
    private final FileValidator fileValidator;
    private final FileService fileService;
    private final SongService songService;

    public AlbumServiceImpl(AlbumRepository albumRepository, FileService fileService, SongService songService,
                            ApplicationConfiguration applicationConfiguration) {
        this.albumRepository = albumRepository;
        this.fileService = fileService;
        this.songService = songService;
        this.fileConfiguration = applicationConfiguration.getFileConfiguration();
        this.fileValidator = new ImageValidator(fileConfiguration);
    }

    @Override
    public Album getById(Long id) throws AlbumNotFoundException {
        return albumRepository.findById(id).get();
    }

    @Override
    public void create(Album album) {
        try {
            albumRepository.save(album);
        } catch (Exception e) {
            throw new AlbumCreationFailed(e.getMessage());
        }
    }

    @Override
    public List<Album> readAll() {
        return albumRepository.findAll();
    }

    @Override
    public List<Album> search(String search) {
        return albumRepository.searchByName(search);
    }

    private Album updateCommonFields(Album update) throws AlbumNotFoundException {
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
    public Album partialUpdateAlbum(Album update) throws AlbumNotFoundException {
        Album updated = updateCommonFields(update);
        albumRepository.save(updated);
        return updated;
    }

    @Override
    public Album fullUpdateAlbum(Album update) throws AlbumNotFoundException {
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

    @Override
    public void delete(Album album) {
        if (album.getUuid() != null) {
            try {
                fileService.delete(album.getUuid(), fileValidator);
            } catch (DestroyFailedException e) {
                e.printStackTrace();
            }
        }
        songService.deleteByAlbum(album);
        albumRepository.deleteById(album.getId());
    }

    @Override
    public Album setCover(Long id, String uuid) {
        Album album = getById(id);
        album.setUuid(uuid);
        albumRepository.save(album);
        return album;
    }

    @Override
    public List<Album> getByArtist(Artist artist) {
        return albumRepository.findAllByArtist(artist);
    }

    @Override
    public List<Album> getAlbumsByOwnerId(Long id) {
        return albumRepository.findAllByOwnerID(id);
    }

    @Override
    public void deleteByArtist(Artist artist) throws DestroyFailedException {
        List<Album> albums = getByArtist(artist);
        for (Album album : albums) {
            delete(album);
        }
        albumRepository.deleteByArtist(artist);
    }

}
