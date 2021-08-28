package vboled.netcracker.musicstreamer.model;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User account;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
    }
}
