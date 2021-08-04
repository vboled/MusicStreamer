package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByNameLike(String search);
}
