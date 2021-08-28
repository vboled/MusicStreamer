package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Like;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.LikeService;
import vboled.netcracker.musicstreamer.service.SongService;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;
    private final User user;
    private final SongService songService;
    private final UserService userService;

    public LikeController(LikeService likeService, User user, SongService songService, UserService userService) {
        this.likeService = likeService;
        this.user = user;
        this.songService = songService;
        this.userService = userService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:perm')")
    public List<Like> getAllLikes() {
        return likeService.getAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> setLike(/*@AuthenticationPrincipal User user,*/
                        Long songID) {
        try {
            Song song = songService.getById(songID);
            return new ResponseEntity<>(likeService.create(song, user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No such song or user",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> deleteLike(/*@AuthenticationPrincipal User user,*/
            Long likeID) {
        try {
            Like like = likeService.getById(likeID);
            likeService.delete(like);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No such song or user",HttpStatus.NOT_FOUND);
        }
    }
}
