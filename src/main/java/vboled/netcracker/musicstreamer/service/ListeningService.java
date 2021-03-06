package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.Region;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.view.ListeningView;

import java.util.List;
import java.util.Map;

public interface ListeningService {

    Listening create(Listening listening);

    Listening getById(Long id);

    Listening updateSeconds(Long id, Long seconds);

    List<Listening> getAllByUser(User user);

    List<Listening> getAllByRegion(Region region);

    List<Listening> getAllByArtist(Long artistID);

    List<Listening> getAll();

    Listening getLatest(User user);

    List<ListeningView> getAllByArtistByRegions(Long artistID);

    void deleteBySong(Song song);
}
