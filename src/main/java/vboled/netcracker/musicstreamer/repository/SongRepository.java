package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.Song;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByUuid(String uuid);

    @Query(
            value = "SELECT * FROM songs WHERE title ILIKE '%' || ?1 || '%'",
            nativeQuery = true
    )
    List<Song> searchByTitle(String search);

    List<Song> findAllByArtist(Artist artist);

    List<Song> findAllByAlbum(Album album);

    @Transactional
    void deleteByAlbum(Album album);

    @Query(
            value = "select * from songs s where s.id in (select l.song_id from likes l, users u where l.user_id = u.id AND u.region_id = ?1\n" +
                    "            group by l.song_id\n" +
                    "            ORDER BY count(*) DESC\n" +
                    "    LIMIT 15)",
            nativeQuery = true
    )
    List<Song> getRegionTop(Long regionID);

    @Query(
            value = "select s.* from artists a, songs s\n" +
                    "    where a.id = s.artist_id\n" +
                    "    and s.id in (select l.song_id from likes l, users u where l.user_id = u.id AND u.id = ?1)\n" +
                    "    ORDER BY random()\n" +
                    "    LIMIT 15",
            nativeQuery = true
    )
    List<Song> getRecByLikedArtists(Long userID);
}
