package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.GeneralInfo;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralInfoService {
    Object getGeneralInfo();

    GeneralInfo updateGeneralInfo(MultipartFile imageFile, String timeWork, String description);
}
