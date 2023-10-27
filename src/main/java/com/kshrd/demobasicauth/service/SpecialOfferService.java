package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.SpecialOffer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SpecialOfferService {
    List<SpecialOffer> getSpecialOffer();

    SpecialOffer insertSpecialOffer(String title, String price, String duration, List<MultipartFile> imgList) throws IOException;

    void deleteSpecialOffer(Long id) throws IOException;
}
