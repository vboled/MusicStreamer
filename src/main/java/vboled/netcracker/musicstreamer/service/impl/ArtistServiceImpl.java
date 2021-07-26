package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.repository.ArtistRepository;
import vboled.netcracker.musicstreamer.service.ArtistService;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    @Override
    public List<Artist> search(String search) {
        return artistRepository.findAllByNameLike(search);
    }
}
