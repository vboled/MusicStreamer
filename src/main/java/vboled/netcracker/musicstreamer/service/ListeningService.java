package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;

public interface ListeningService {

    Listening create(Listening listening);

    Listening getById(Long id);

    Listening updateSeconds(Long id, Long seconds);

    List<Listening> getAllByUser(User user);

    List<Listening> getAllByArtist(Long artistID);

    List<Listening> getAll();

    Listening getLatest(User user);
}
