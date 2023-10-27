package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.Slider;
import com.kshrd.demobasicauth.model.request.SliderRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SliderService {
    List<Slider> getSlider();

    Object insertSlider(MultipartFile imageFile, String title) throws IOException;

    Slider updateSlider(MultipartFile file, Long id, String title);

    void deleteSlider(Long id) throws IOException;

    Slider getSliderById(Long id);
}
