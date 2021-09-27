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
            value = "select * from songs s where s.id in (select l.song_id from likes l, users u where u.id != ?1 AND l.user_id = u.id AND u.region_id = ?2\n" +
                    "            group by l.song_id\n" +
                    "            ORDER BY count(*) DESC\n" +
                    "    LIMIT 15)",
            nativeQuery = true
    )
    List<Song> getRegionTop(Long userID, Long regionID);

    @Query(
            value = "with id as (select\n" +
                    "    a.id as artist_id, l.song_id as song_id\n" +
                    "    from\n" +
                    "    likes l, artists a, songs s\n" +
                    "    where\n" +
                    "    l.user_id = ?1 AND l.song_id = s.id AND\n" +
                    "    s.artist_id = a.id)\n" +
                    "\n" +
                    "select\n" +
                    "       s.*\n" +
                    "from\n" +
                    "     songs s, artists a\n" +
                    "where\n" +
                    "      s.artist_id = a.id AND a.id in (select artist_id from id)\n" +
                    "      and s.id not in (select song_id from id)\n" +
                    "ORDER BY random()\n" +
                    "LIMIT 20",
            nativeQuery = true
    )
    List<Song> getRecByLikedArtists(Long userID);

    @Query(
            value = "with id as (\n" +
                    "    select\n" +
                    "g.id as genre_id, l.song_id as song_id\n" +
                    "from\n" +
                    "likes l, albums a, songs s, genres g\n" +
                    "where\n" +
                    "l.user_id = ?1 AND l.song_id = s.id AND\n" +
                    "s.album_id = a.id and g.id = a.genre_id\n" +
                    "    )\n" +
                    "\n" +
                    "select\n" +
                    "       s.*\n" +
                    "from\n" +
                    "     songs s, albums a\n" +
                    "where\n" +
                    "    s.album_id = a.id and\n" +
                    "    a.genre_id in (select id.genre_id from id) and\n" +
                    "    s.id not in (select id.song_id from id)\n" +
                    "order by random()\n" +
                    "limit 20",
            nativeQuery = true
    )
    List<Song> getRecByLikedGenres(Long userID);
}
