package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.Artist;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(
            value = "SELECT * FROM artists WHERE name ILIKE '%' || ?1 || '%'",
            nativeQuery = true
    )
    List<Artist> searchByName(String search);

    List<Artist> findAllByOwnerID(Long id);
}
