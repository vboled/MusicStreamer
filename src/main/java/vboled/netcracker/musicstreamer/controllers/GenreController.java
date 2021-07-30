package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Genre;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.GenreService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Value("${max.genre.name.length}")
    private Long MAX_GENRE_NAME_LENGTH;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> createGenre(@RequestBody Genre genre) {

        if (genre.getName().length() > MAX_GENRE_NAME_LENGTH)
            return new ResponseEntity<>("Too long name!!!", HttpStatus.BAD_REQUEST);

        if (genreService.existByName(genre.getName()))
            return new ResponseEntity<>("Genre already exist!!!", HttpStatus.NOT_MODIFIED);

        genreService.create(genre);
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllGenre() {
        final List<Genre> genres = genreService.readAll();
        if (genres == null || genres.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getGenre(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(genreService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Genre not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> updateGenre(@RequestParam Long id, @RequestParam String name) {
        if (genreService.existByName(name))
            return new ResponseEntity<>("Genre already exist!!!", HttpStatus.NOT_MODIFIED);
        if (genreService.updateName(id, name))
            return new ResponseEntity<>(name, HttpStatus.OK);
        return new ResponseEntity<>("Genre not exist", HttpStatus.NOT_MODIFIED);
    }
}
