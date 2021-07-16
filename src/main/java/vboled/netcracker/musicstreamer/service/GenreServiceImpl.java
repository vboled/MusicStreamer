package vboled.netcracker.musicstreamer.service;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Genre;
import vboled.netcracker.musicstreamer.repository.GenreRepository;

import java.util.NoSuchElementException;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public boolean existByName(String name) {
        return genreRepository.existsByName(name);
    }

    @Override
    public Genre getById(int id) throws NoSuchElementException {
        return genreRepository.findById(id).get();
    }

    @Override
    public boolean updateName(int id, String name) {
        if (genreRepository.existsById(id)) {
            Genre genre = genreRepository.getById(id);
            genre.setName(name);
            genreRepository.save(genre);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(Genre genre) {
        genreRepository.save(genre);
        return true;
    }

    @Override
    public boolean existById(int id) {
        return genreRepository.existsById(id);
    }
}
