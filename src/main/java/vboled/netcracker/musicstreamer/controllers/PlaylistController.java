package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.PlaylistService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/playlist/")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllPlaylists() {
        final List<Playlist> playlists = playlistService.readAll();
        if (playlists == null || playlists.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> getPlaylist(@RequestParam Long id) {
        //add check
        try {
            return new ResponseEntity<>(playlistService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
        }
    }
}
