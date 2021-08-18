package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.Song;

import java.util.List;

public interface AddedSongRepository extends JpaRepository<AddedSong, Long> {

    List<AddedSong> findAllByPlaylistOrderByAddDate(Playlist playList);

    boolean existsByPlaylistAndSong(Playlist playList, Song Song);
}
