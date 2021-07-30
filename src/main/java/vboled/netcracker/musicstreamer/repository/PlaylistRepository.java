package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.Playlist;

import javax.transaction.Transactional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

//    @Transactional
//    @Query(
//        value = "DELETE FROM added_songs WHERE playlist_id = ?1 " +
//                "DELETE FROM playlists WHERE id = ?1",
//        nativeQuery = true)
    void deleteById(Long id);
}