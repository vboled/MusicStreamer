package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.AudioValidator;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.repository.SongRepository;
import vboled.netcracker.musicstreamer.service.AddedSongService;
import vboled.netcracker.musicstreamer.service.FileService;
import vboled.netcracker.musicstreamer.service.LikeService;
import vboled.netcracker.musicstreamer.service.SongService;
import vboled.netcracker.musicstreamer.view.SongView;

import javax.security.auth.DestroyFailedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private final FileValidator fileValidator = new AudioValidator();
    private final SongRepository songRepository;
    private final AddedSongService addedSongService;
    private final FileService fileService;
    private final LikeService likeService;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, AddedSongService addedSongService, FileService fileService, LikeService likeService) {
        this.songRepository = songRepository;
        this.addedSongService = addedSongService;
        this.fileService = fileService;
        this.likeService = likeService;
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
    public void delete(Song song) {
        addedSongService.deleteBySong(song);
        if (song.getUuid() != null) {
            try {
                fileService.delete(song.getUuid(), fileValidator);
            } catch (DestroyFailedException e) {
                e.printStackTrace();
            }
        }
        songRepository.deleteById(song.getId());
    }

    @Override
    public List<Song> readAll() {
        return songRepository.findAll();
    }

    private Song updateCommonFields(Song update) {
//        if (!songRepository.existsByUuid(update.getUuid()))
//            throw new NoSuchElementException("No song with such id");
        Song songToUpdate = songRepository.findById(update.getId()).get();
        if (update.getWords() != null) {
            songToUpdate.setWords(update.getWords());
        }
        if (update.getAuthor() != null) {
            songToUpdate.setAuthor(update.getAuthor());
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

    @Override
    public Song getById(Long id) {
        return songRepository.findById(id).get();
    }

    @Override
    public Song setSongFile(Long id, String uuid) {
        Song song = getById(id);
        song.setUuid(uuid);
        song.setAvailable(true);
        songRepository.save(song);
        return song;
    }

    @Override
    public List<Song> getByArtist(Artist artist) {
        return songRepository.findAllByArtist(artist);
    }

    @Override
    public List<Song> getByAlbum(Album album) {
        return songRepository.findAllByAlbum(album);
    }

    List<SongView> getSongView(List<Song> songs, User user) {
        return songs.stream().map(a -> new SongView(a, likeService.getLike(a, user))).collect(Collectors.toList());
    }

    @Override
    public List<SongView> getByArtist(Artist artist, User user) {
        return getSongView(songRepository.findAllByArtist(artist), user);
    }

    @Override
    public List<SongView> getByAlbum(Album album, User user) {
        return getSongView(songRepository.findAllByAlbum(album), user);
    }

    @Override
    public void deleteByAlbum(Album album) {
        List<Song> songs = getByAlbum(album);
        for (Song song:songs) {
            delete(song);
        }
    }

    @Override
    public void deleteAudio(Song song) {
        song.setUuid(null);
        song.setAvailable(false);
        songRepository.save(song);
    }
}
