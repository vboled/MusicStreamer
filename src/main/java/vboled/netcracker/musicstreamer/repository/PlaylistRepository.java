package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
