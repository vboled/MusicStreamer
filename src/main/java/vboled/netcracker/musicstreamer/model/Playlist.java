package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;

//@Entity
@Data
@Table(name = "playlists")
public class Playlist {

    @Id
    private Long id;

    @Column(name = "owner_id")
    private Long ownerID;

//    @OneToMany
//    @JoinColumn()
//    private AddedSong addedSong;

    @Column(name = "name")
    private String name;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "description")
    private String description;

    @Column(name = "is_main")
    private boolean isMain;


}
