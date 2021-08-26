package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.Playlist;

import javax.transaction.Transactional;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Transactional
    void deleteById(Long id);

    @Query(
            value = "SELECT * FROM playlists p WHERE p.owner_id = ?1",
            nativeQuery = true
    )
    List<Playlist> findAllPlaylistsByOwner(Long id);
}
