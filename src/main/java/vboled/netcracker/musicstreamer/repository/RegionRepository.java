package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Region;

import java.util.List;


public interface RegionRepository extends JpaRepository<Region, Long> {

    Region getByName(String name);

    List<Region> findAll();
}
