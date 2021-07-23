package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vboled.netcracker.musicstreamer.model.Song;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    boolean existsByUuid(String uuid);

    Optional<Song> findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);
}
