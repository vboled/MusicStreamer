package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class GeneralController {

    private final UserService userService;
    private final SongService songService;
    private final GenreService genreService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    @Autowired
    public GeneralController(UserService userService, SongService songService, GenreService genreService,
                             ArtistService artistService, AlbumService albumService) {
        this.userService = userService;
        this.songService = songService;
        this.genreService = genreService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @PostMapping("create/")
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            userService.create(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("search/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> search(@RequestParam String search) {
        System.out.println(search);
        Map<String, List<?>> res = new HashMap<>();
        res.put("Songs: ", songService.search(search));
        res.put("Artists: ", artistService.search(search));
        res.put("Genres: ", genreService.search(search));
        res.put("Albums: ", albumService.search(search));
        System.out.println(res);
        res.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return new ResponseEntity<>(
                res,
                HttpStatus.OK);
    }
}