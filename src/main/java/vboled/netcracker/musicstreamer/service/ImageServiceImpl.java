package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Image;
import vboled.netcracker.musicstreamer.repository.ImageRepository;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public void create(Image image) {
        imageRepository.save(image);
    }

    @Override
    public List<Image> readAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image read(int id) {
        return null;
    }

    @Override
    public boolean update(Image image, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
