package com.springboot.webdejuegos.services.impl;

import com.springboot.webdejuegos.entity.Image;
import com.springboot.webdejuegos.repository.ImageRepository;
import com.springboot.webdejuegos.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(long id) {
        return imageRepository.findById(id).get();
    }
}
