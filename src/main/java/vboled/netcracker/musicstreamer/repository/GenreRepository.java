package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre getById(int id);

    boolean existsByName(String name);

    boolean existsById(int id);
}
