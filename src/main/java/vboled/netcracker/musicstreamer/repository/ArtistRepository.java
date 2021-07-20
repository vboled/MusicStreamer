package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
