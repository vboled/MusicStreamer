package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Like;
import vboled.netcracker.musicstreamer.model.Song;

@Data
public class SongView {

    private Song song;
    private Like like;

    public SongView(Song song, Like like) {
        this.song = song;
        this.like = like;
    }
}
