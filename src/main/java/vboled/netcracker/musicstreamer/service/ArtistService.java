package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Artist;

import java.util.List;

public interface ArtistService {

    List<Artist> search(String search);
}
