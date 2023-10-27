package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.PopularTour;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PopularTourService {
    List<PopularTour> getPopularTour();

    Object getPopularTourById(Long id);

    PopularTour insertPopularTour(MultipartFile imageFile, String title, String duration, String price) throws IOException;

    PopularTour updatePopularTour(MultipartFile imageFile, Long id, String title, String duration, String price);

    void deletePopularTour(Long id) throws IOException;
}
