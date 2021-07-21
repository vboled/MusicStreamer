package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vboled.netcracker.musicstreamer.model.Song;

import javax.transaction.Transactional;

public interface SongRepository extends JpaRepository<Song, Integer> {

    boolean existsByUuid(String uuid);

    Song findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);
}
