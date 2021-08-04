package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "added_songs")
public class AddedSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne()
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @PrePersist
    public void prePersist() {
        addDate = LocalDateTime.now();
    }
}
