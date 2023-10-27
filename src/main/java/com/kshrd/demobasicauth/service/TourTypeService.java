package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.TourType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TourTypeService {
    List<TourType> getTourType();

    Object getTourTypeById(Long id);

    TourType insertTourType(MultipartFile imageFile, String title, String description) throws IOException;

    void deleteTourType(Long id) throws IOException;

    TourType updateTourType(MultipartFile imageFile, Long id, String title, String description);
}
