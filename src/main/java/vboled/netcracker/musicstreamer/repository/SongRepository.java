package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Song;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByUuid(String uuid);

    @Query(
            value = "SELECT * FROM songs WHERE title ILIKE '%' || ?1 || '%'",
            nativeQuery = true
    )
    List<Song> searchByTitle(String search);

    List<Song> findAllByArtist(Artist artist);

    List<Song> findAllByAlbum(Album album);

    @Transactional
    void deleteByAlbum(Album album);
}
