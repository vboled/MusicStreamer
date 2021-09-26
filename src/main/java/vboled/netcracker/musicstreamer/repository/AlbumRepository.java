package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;

import javax.transaction.Transactional;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query(
            value = "SELECT * FROM albums WHERE name ILIKE '%' || ?1 || '%'",
            nativeQuery = true
    )
    List<Album> searchByName(String search);

    List<Album> findAllByArtist(Artist artist);

    List<Album> findAllByOwnerID(Long id);

    @Transactional
    void deleteByArtist(Artist artist);
}
