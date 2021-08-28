package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.service.LikeService;
import vboled.netcracker.musicstreamer.view.PlaylistView;
import vboled.netcracker.musicstreamer.exceptions.SongAlreadyExistException;
import vboled.netcracker.musicstreamer.model.AddedSong;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.model.validator.ImageValidator;
import vboled.netcracker.musicstreamer.service.AddedSongService;
import vboled.netcracker.musicstreamer.service.PlaylistService;
import vboled.netcracker.musicstreamer.service.SongService;
import vboled.netcracker.musicstreamer.service.impl.FileServiceImpl;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.DestroyFailedException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final AddedSongService addedSongService;
    private final FileServiceImpl fileService;
    private final FileValidator fileValidator = new ImageValidator();
    private final LikeService likeService;
    private final User user;

    public PlaylistController(PlaylistService playlistService, SongService songService, AddedSongService addedSongService, FileServiceImpl fileService, LikeService likeService, User user) {
        this.playlistService = playlistService;
        this.songService = songService;
        this.addedSongService = addedSongService;
        this.fileService = fileService;
        this.likeService = likeService;
        this.user = user;
    }

    void checkAdminOrOwnerPerm(User user, Long id) throws IllegalAccessError {
        Set<Permission> perm = user.getRole().getPermissions();
        if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                (perm.contains(Permission.OWNER_PERMISSION) &&
                        user.getId().equals(playlistService.getById(id).getOwnerID())))) {
            throw new IllegalAccessError();
        }
    }

    void checkAdminOrUserPerm(User user, Long id) throws IllegalAccessError {
        Set<Permission> perm = user.getRole().getPermissions();
        if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                (perm.contains(Permission.USER_CHANGE) &&
                        user.getId().equals(playlistService.getById(id).getOwnerID())))) {
            throw new IllegalAccessError();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> createPlaylist(/*@AuthenticationPrincipal User user,*/
                                     @RequestBody Playlist playlist) {
//        if (!user.getRole().getPermissions().contains(Permission.ADMIN_PERMISSION))
            playlist.setOwnerID(user.getId());
        return new ResponseEntity<>(playlistService.create(playlist), HttpStatus.OK);
    }

    @GetMapping("/songs/all")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllSongsByPlaylist(/*@AuthenticationPrincipal User user,*/
                                      @RequestParam Long playlistID) {
        try {
            final List<AddedSong> songs = addedSongService.getAllByPlaylist(playlistService.getById(playlistID));
            if (songs == null || songs.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllPlaylists() {
        final List<Playlist> playlists = playlistService.readAll();
        if (playlists == null || playlists.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @PutMapping("/add/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> addSong(/*@AuthenticationPrincipal User user,*/
                              @RequestParam Long songID, @RequestParam Long playlistID) {
        try {
            checkAdminOrUserPerm(user, playlistID);
            Playlist playlist = playlistService.getById(playlistID);
            Song song = songService.getById(songID);
            return new ResponseEntity<>(addedSongService.addSongToPlaylist(playlist, song), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Playlist or Song not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        } catch (SongAlreadyExistException e) {
            return new ResponseEntity<>("Song exist in playlist", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/add/main/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> addSongToMain(/*@AuthenticationPrincipal User user,*/
            @RequestParam Long songID) {
        try {
            Song song = songService.getById(songID);
            Playlist main = playlistService.getMainPlaylistByUserId(user.getId());
            ResponseEntity<?> res = addSong(songID, main.getId());
            if (!res.getStatusCode().equals(HttpStatus.OK))
                return res;
            likeService.create(song, user);
            return res;
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Playlist or Song not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        } catch (SongAlreadyExistException e) {
            return new ResponseEntity<>("Song exist in playlist", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deletePlaylist(/*@AuthenticationPrincipal User user,*/
                                     @RequestParam Long playlistID) {
        try {
            checkAdminOrUserPerm(user, playlistID);
            Playlist playlist = playlistService.getById(playlistID);
            if (playlist.isMain())
                return new ResponseEntity<>("It's default playlist", HttpStatus.NOT_MODIFIED);
            ResponseEntity<?> deleteRes = deletePlaylistCover(playlistID);
            if (!deleteRes.getStatusCode().equals(HttpStatus.OK))
                return deleteRes;
            playlistService.delete(playlist);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No such playlist", HttpStatus.NOT_FOUND);
        }
        catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/song/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deleteSong(/*@AuthenticationPrincipal User user,*/
                                 @RequestParam Long addedSongId) {
        try {
            AddedSong added = addedSongService.getById(addedSongId);
            checkAdminOrOwnerPerm(user, added.getPlaylist().getId());
            if (added.getPlaylist().isMain())
                likeService.delete(added.getSong(), user);
            addedSongService.deleteSong(added);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No such added song", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/song/main/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deleteSongFromMain(/*@AuthenticationPrincipal User user,*/
            @RequestParam Long songId) {
        try {
            Song song = songService.getById(songId);
            Playlist main = playlistService.getMainPlaylistByUserId(user.getId());
            return deleteSong(addedSongService.getBySongAndPlaylist(song, main).getId());
        } catch (EntityNotFoundException | NoSuchElementException e) {
            return new ResponseEntity<>("No such added song", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> getPlaylist(/*@AuthenticationPrincipal User user,*/ @RequestParam Long id) {
        try {
            checkAdminOrUserPerm(user, id);
            return new ResponseEntity<>(new PlaylistView(playlistService.getById(id),
                    addedSongService.getAllByPlaylist(playlistService.getById(id))), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cover/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> getPlaylistCover(/*@AuthenticationPrincipal User user,*/
                                    @RequestParam Long id) {
        try {
            checkAdminOrUserPerm(user, id);
            return fileService.read(playlistService.getById(id).getUuid(), fileValidator);
        } catch (IOException e) {
            return new ResponseEntity<>("Cover not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cover/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deletePlaylistCover(/*@AuthenticationPrincipal User user,*/
                                          @RequestParam Long id) {
        try {
            checkAdminOrUserPerm(user, id);
            Playlist pl = playlistService.getById(id);
            String uuid = pl.getUuid();
            if (uuid == null)
                return new ResponseEntity<>(HttpStatus.OK);
            fileService.delete(uuid, fileValidator);
            playlistService.partialUpdatePlaylist(pl);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (DestroyFailedException e) {
            return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cover/{id}")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> uploadPlaylistCover(/*@AuthenticationPrincipal User user,*/
            @PathVariable Long id, @RequestParam MultipartFile file) {
        try{
            checkAdminOrUserPerm(user, id);
            String name = fileService.uploadFile(file, fileValidator, UUID.randomUUID().toString());
            return new ResponseEntity<>(playlistService.setCover(id, name), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Playlist not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cover/update/{id}")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> updatePlaylistCover(/*@AuthenticationPrincipal User user,*/
                                       @PathVariable Long id, @RequestParam MultipartFile file) {
//        ResponseEntity<?> res = deletePlaylistCover(user, id);
        ResponseEntity<?> res = deletePlaylistCover(id);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
//        return uploadPlaylistCover(user, id, file);
        return uploadPlaylistCover(id, file);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> updatePlaylist(/*@AuthenticationPrincipal User user,*/
            @RequestBody Playlist playlist) {
        try{
            Playlist res = playlistService.fullUpdatePlaylist(playlist);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
