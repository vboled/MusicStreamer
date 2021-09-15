package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.ListeningRepository;
import vboled.netcracker.musicstreamer.service.ListeningService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ListeningServiceImpl implements ListeningService {

    private final ListeningRepository listeningRepository;

    public ListeningServiceImpl(ListeningRepository listeningRepository) {
        this.listeningRepository = listeningRepository;
    }


    @Override
    public Listening create(Listening listening) {
        try {
            return listeningRepository.save(listening);
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Listening getById(Long id) {
        return listeningRepository.findById(id).get();
    }

    @Override
    public Listening updateSeconds(Long id, Long seconds) {
        Listening update = getById(id);
        update.setSeconds(seconds);
        return listeningRepository.save(update);
    }

    @Override
    public List<Listening> getAllByUser(User user) {
        return listeningRepository.findAllByUser(user);
    }

    @Override
    public List<Listening> getAllByArtist(Long artistID) {
        return listeningRepository.findAllByArtistID(artistID);
    }

    @Override
    public List<Listening> getAll() {
        return listeningRepository.findAll();
    }
}
