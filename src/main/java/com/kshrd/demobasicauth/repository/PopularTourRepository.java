package com.kshrd.demobasicauth.repository;

import com.kshrd.demobasicauth.model.PopularTour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularTourRepository extends JpaRepository<PopularTour, Long> {
}
