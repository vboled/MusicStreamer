package vboled.netcracker.musicstreamer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @JoinColumn(name = "name")
    private String name;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "code")
    private String code;

}