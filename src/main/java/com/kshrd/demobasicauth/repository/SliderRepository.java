package com.kshrd.demobasicauth.repository;

import com.kshrd.demobasicauth.model.Slider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SliderRepository extends JpaRepository<Slider, Long> {

    Slider findByTitle(String title);
}
