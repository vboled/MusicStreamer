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
@RequestMapping("/api/v1/")
public class GeneralController {

    private final UserService userService;
    private final SongService songService;
    private final GenreService genreService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final PlaylistService playlistService;

    @Autowired
    public GeneralController(UserService userService, SongService songService, GenreService genreService,
                             ArtistService artistService, AlbumService albumService, PlaylistService playlistService) {
        this.userService = userService;
        this.songService = songService;
        this.genreService = genreService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.playlistService = playlistService;
    }

    @PostMapping("create/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            User res = userService.create(user);
            playlistService.createMainPlaylist(res.getId());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("search/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> search(@RequestParam String search) {
        Map<String, List<?>> res = new HashMap<>();
        res.put("songs", songService.search(search));
        res.put("artists", artistService.search(search));
        res.put("genres", genreService.search(search));
        res.put("albums", albumService.search(search));
        res.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return new ResponseEntity<>(
                res,
                HttpStatus.OK);
    }
}
