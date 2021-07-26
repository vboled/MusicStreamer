package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @Column(name = "owner_id")
    private Long ownerID;

    @Column(name = "name")
    private String name;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "edit_date")
    private Date editDate;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
    }
}
