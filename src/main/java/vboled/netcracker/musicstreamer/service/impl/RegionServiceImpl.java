package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Region;
import vboled.netcracker.musicstreamer.repository.RegionRepository;
import vboled.netcracker.musicstreamer.service.RegionService;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region getByName(String name) {
        return regionRepository.getByName(name);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }
}
