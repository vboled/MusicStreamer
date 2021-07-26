package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
