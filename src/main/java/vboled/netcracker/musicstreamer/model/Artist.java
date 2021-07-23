package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
