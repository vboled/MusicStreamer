package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Genre;
import vboled.netcracker.musicstreamer.repository.GenreRepository;
import vboled.netcracker.musicstreamer.service.GenreService;

import java.util.List;
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
    public Genre getById(Long id) throws NoSuchElementException {
        return genreRepository.findById(id).get();
    }

    @Override
    public boolean updateName(Long id, String name) {
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
    public List<Genre> readAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> search(String search) {
        return genreRepository.findAllByNameLike(search);
    }
}
