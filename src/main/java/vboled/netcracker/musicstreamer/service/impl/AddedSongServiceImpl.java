package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.repository.AddedSongRepository;
import vboled.netcracker.musicstreamer.service.AddedSongService;

import java.util.List;

@Service
public class AddedSongServiceImpl implements AddedSongService {

    private final AddedSongRepository addedSongRepository;

    public AddedSongServiceImpl(AddedSongRepository addedSongRepository) {
        this.addedSongRepository = addedSongRepository;
    }

    @Override
    public List<AddedSong> getAllByPlaylist(Playlist playlist) {
        return addedSongRepository.findAllByPlaylistOrderByAddDate(playlist.getId());
    }

    @Override
    public AddedSong addSongToPlaylist(Playlist playlist, Song song) {
        AddedSong add = new AddedSong();
        add.setPlaylist(playlist);
        add.setSong(song);
        addedSongRepository.save(add);
        return add;
    }

    @Override
    public void deleteSong(AddedSong addedSong) {
        addedSongRepository.deleteById(addedSong.getId());
    }

    @Override
    public AddedSong getById(Long addedSongID) {
        return addedSongRepository.getById(addedSongID);
    }
}
