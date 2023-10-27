package com.kshrd.demobasicauth.repository;

import com.kshrd.demobasicauth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);
}
