package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.GeneralInfo;
import com.kshrd.demobasicauth.repository.GeneralInfoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.compare.ComparableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralInfoServiceImpl implements GeneralInfoService{
    private final GeneralInfoRepository generalInfoRepository;
    private final Path root = Paths.get("src/main/resources/images/");
    private static final Logger logger = LoggerFactory.getLogger(SliderServiceImpl.class);

    @Override
    public Object getGeneralInfo() {
        return generalInfoRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionClass("Can't find object info."));
    }

    @Override
    public GeneralInfo updateGeneralInfo(MultipartFile file, String timeWork, String description) {
        GeneralInfo existInfo = generalInfoRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionClass("general info Object not found."));
        existInfo.setTimeWork(timeWork);
        existInfo.setDescription(description);

        //update images
        if (file != null) {
            String fileName = existInfo.getImage();
            Path imagePath = root.resolve(fileName);

            // Delete the old image file
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }

            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
            try {
                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                existInfo.setImage(fileName);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }
        }
        return generalInfoRepository.save(existInfo);
    }
}
