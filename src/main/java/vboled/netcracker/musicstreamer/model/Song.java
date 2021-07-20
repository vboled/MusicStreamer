package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "uuid")
    private String uuid;

    @Column(name = "owner_id")
    private int ownerID;

    @Column(name = "album_id")
    private int albumID;

    @Column(name = "main_artist_id")
    private int mainArtistId;

    @Column(name = "secondary_artist_id")
    private int secondaryArtistId;

    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "duration")
    private int duration;

    @Column(name = "words")
    private String words;

    @Column(name = "author")
    private String author;

    @Column(name = "volume")
    private int volume;

    @Column(name = "reliase_date")
    private LocalDateTime realiseDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;
}
