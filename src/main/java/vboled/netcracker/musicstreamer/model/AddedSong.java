package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

//@Entity
@Data
@Table(name = "addedSongs")
public class AddedSong {
    @Id
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @Column(name = "owner_id")
    private Long ownerID;

    @Column(name = "playlist_id")
    private Long playlistID;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @PrePersist
    public void prePersist() {
        addDate = LocalDateTime.now();
    }
}
