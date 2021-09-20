package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.Region;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.transaction.Transactional;
import java.util.List;

public interface ListeningRepository extends JpaRepository<Listening, Long> {

    List<Listening> findAllByUser(User user);

    List<Listening> findAllBySong(Song song);

    @Query(
            value = "SELECT\n l.id, l.song_id, l.user_id, l.seconds, l.listening_date FROM listenings l, users u WHERE l.user_id = u.id AND u.region_id = ?1",
            nativeQuery = true
    )
    List<Listening> findAllByRegion(Long regionID);

    Listening findFirstByUserOrderByListeningDateDesc(User user);

    @Query(
            value = "SELECT\n l.id, l.song_id, l.user_id, l.seconds, l.listening_date FROM listenings l, songs s WHERE l.song_id = s.id AND s.artist_id = ?1",
            nativeQuery = true
    )
    List<Listening> findAllByArtistID(Long artistID);

    @Transactional
    void deleteBySong(Song song);
}
