package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Artist;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findAllByNameLike(String search);
}
