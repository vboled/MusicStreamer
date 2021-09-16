package vboled.netcracker.musicstreamer.view;

import lombok.Data;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.Region;

import java.util.List;

@Data
public class ListeningView {
    List<Listening> listenings;

    Region region;

    public ListeningView(List<Listening> listening, Region region) {
        this.listenings = listening;
        this.region = region;
    }
}
