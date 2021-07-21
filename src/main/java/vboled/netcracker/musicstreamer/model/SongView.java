package vboled.netcracker.musicstreamer.model;

import lombok.Data;

@Data
public class SongView {
    private Song song;
    private Genre genre;
    private Artist artist;
}
