package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
