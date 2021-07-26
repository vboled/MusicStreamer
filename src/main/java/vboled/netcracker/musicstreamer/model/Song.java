package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "owner_id")
    private Long ownerID;

    @ManyToOne()
    @JoinColumn(name="album_id")
    private Album album;

    @Column(name = "main_artist_id")
    private Long mainArtistId;

    @Column(name = "secondary_artist_id")
    private Long secondaryArtistId;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "title")
    private String title;

    // need to be generated automatically
    @Column(name = "duration")
    private Long duration;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "words")
    private String words;

    @Column(name = "author")
    private String author;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    // need to be generated automatically
    @Column(name = "edit_date")
    private Date editDate;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
    }
}
