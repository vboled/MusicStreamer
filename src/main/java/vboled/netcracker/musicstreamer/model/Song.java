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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "owner_id")
    private Long ownerID;

    @ManyToOne()
    @JoinColumn(name="album_id")
    private Album album;

    @ManyToOne()
    @JoinColumn(name="artist_id")
    private Artist artist;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "words")
    private String words;

    @Column(name = "author")
    private String author;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
        editDate = createDate;
    }

    @PreUpdate
    public void preUpdate() { editDate = LocalDateTime.now(); }
}
