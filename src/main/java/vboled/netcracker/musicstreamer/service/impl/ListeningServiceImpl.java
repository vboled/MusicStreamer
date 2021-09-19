package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.Region;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.ListeningRepository;
import vboled.netcracker.musicstreamer.service.ListeningService;
import vboled.netcracker.musicstreamer.service.RegionService;
import vboled.netcracker.musicstreamer.view.ListeningView;

import java.sql.Ref;
import java.util.*;

@Service
public class ListeningServiceImpl implements ListeningService {

    private final ListeningRepository listeningRepository;
    private final RegionService regionService;

    public ListeningServiceImpl(ListeningRepository listeningRepository, RegionService regionService) {
        this.listeningRepository = listeningRepository;
        this.regionService = regionService;
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
    public List<Listening> getAllByRegion(Region region) {
        return listeningRepository.findAllByRegion(region.getId());
    }

    @Override
    public List<Listening> getAllByArtist(Long artistID) {
        return listeningRepository.findAllByArtistID(artistID);
    }

    @Override
    public List<Listening> getAll() {
        return listeningRepository.findAll();
    }

    @Override
    public Listening getLatest(User user) {
        return listeningRepository.findFirstByUserOrderByListeningDateDesc(user);
    }

    @Override
    public List<ListeningView> getAllByArtistByRegions(Long artistID) {
        List<Region> regions = regionService.getAllRegions();
        List<ListeningView> res = new LinkedList<>();
        for (Region r: regions) {
            res.add(new ListeningView(getAllByRegion(r), r));
        }
        return res;
    }
}
