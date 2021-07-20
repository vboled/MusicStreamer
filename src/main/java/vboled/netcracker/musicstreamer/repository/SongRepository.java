package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

    boolean existsByUuid(String uuid);

    void deleteByUuid(String uuid);
}
