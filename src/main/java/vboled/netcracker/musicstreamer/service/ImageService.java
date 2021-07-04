package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.Image;

import java.util.List;

public interface ImageService {
    void create(Image image);

    List<Image> readAll();

    Image read(int id);

    boolean update(Image image, int id);

    boolean delete(int id);
}
