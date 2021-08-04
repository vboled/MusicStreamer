package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "playlists")
public class Playlist {

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

    @Column(name = "description")
    private String description;

    @Column(name = "is_main")
    private boolean isMain;
}
