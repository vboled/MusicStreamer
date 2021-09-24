package vboled.netcracker.musicstreamer.service;


import vboled.netcracker.musicstreamer.model.Region;

import java.util.List;

public interface RegionService {

    Region getByName(String name);

    List<Region> getAllRegions();
}
