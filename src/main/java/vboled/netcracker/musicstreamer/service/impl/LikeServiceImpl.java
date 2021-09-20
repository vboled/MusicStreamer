package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Like;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.LikeRepository;
import vboled.netcracker.musicstreamer.service.LikeService;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like create(Song song, User user) {
        Like like = new Like();
        like.setSong(song);
        like.setAccount(user);
        return likeRepository.save(like);
    }

    @Override
    public Like getLike(Song song, User user) {
        return likeRepository.getBySongAndAccount(song, user);
    }

    @Override
    public void delete(Like like) {
        likeRepository.deleteById(like.getId());
    }

    @Override
    public void delete(Song song, User user) {
        likeRepository.deleteBySongAndAccount(song, user);
    }

    @Override
    public List<Like> getAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like getById(Long likeID) {
        return likeRepository.getById(likeID);
    }

    @Override
    public void deleteBySong(Song song) {
        likeRepository.deleteBySong(song);
    }
}
