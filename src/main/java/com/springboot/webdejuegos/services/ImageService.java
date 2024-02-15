package com.springboot.webdejuegos.services;


import com.springboot.webdejuegos.entity.Image;

import java.util.List;

public interface ImageService {
    public Image save(Image image);
    public List<Image> findAll();
    public Image findById(long id);
}