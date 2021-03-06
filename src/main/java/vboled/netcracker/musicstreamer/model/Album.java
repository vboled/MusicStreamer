package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @Column(name = "owner_id")
    private Long ownerID;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @Column(name = "release_date")
    private Date releaseDate;

    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne()
    @JoinColumn(name="artist_id")
    private Artist artist;

    @Column(name = "name")
    private String name;

    @Column(name = "volumes")
    private Long volumes;

    @Column(name = "type")
    private String type;

    @PrePersist
    public void prePersist() {
        type = "album";
        createDate = LocalDateTime.now();
        editDate = createDate;
    }

    @PreUpdate
    public void preUpdate() { editDate = LocalDateTime.now(); }
}
