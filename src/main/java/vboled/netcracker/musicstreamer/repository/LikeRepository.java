package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.Like;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like getBySongAndAccount(Song song, User user);

    @Transactional
    void deleteBySongAndAccount(Song song, User user);

    @Transactional
    void deleteBySong(Song song);
}
