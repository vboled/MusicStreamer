package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.AddedSong;

import java.util.List;

public interface AddedSongRepository extends JpaRepository<AddedSong, Long> {

    List<AddedSong> findAllByPlaylistOrderByAddDate(Long playListId);
}
