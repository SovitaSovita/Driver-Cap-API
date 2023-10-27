package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.TourType;
import com.kshrd.demobasicauth.model.request.TourTypeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TourTypeService {
    List<TourType> getTourType();

    Object getTourTypeById(Long id);

    TourType insertTourType(MultipartFile imageFile, TourTypeRequest request) throws IOException;
}
