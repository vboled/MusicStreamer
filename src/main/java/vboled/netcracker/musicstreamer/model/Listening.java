package vboled.netcracker.musicstreamer.model;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "listenings")
public class Listening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "seconds")
    private Long seconds;

    @Column(name = "listening_date")
    private LocalDateTime listeningDate;

    @PrePersist
    public void prePersist() {
        listeningDate = LocalDateTime.now();
    }
}
