package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Like;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

public interface LikeService {

    Like create(Song song, User user);

    Like getLike(Song song, User user);

    void delete(Like like);

    void delete(Song song, User user);

    List<Like> getAll();

    Like getById(Long likeID);

    void deleteBySong(Song song);
}
