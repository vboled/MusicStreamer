package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vboled.netcracker.musicstreamer.model.Song;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByUuid(String uuid);

    List<Song> findAllByTitleIsLike(String search);

    @Query(
        value = "SELECT * FROM songs s WHERE s.artist_id = ?1",
        nativeQuery = true)
    List<Song> findAllByArtistId(Long id);

    @Query(
        value = "SELECT * FROM songs s WHERE s.album_id = ?1",
        nativeQuery = true)
    List<Song> findAllByAlbumId(Long id);
}
