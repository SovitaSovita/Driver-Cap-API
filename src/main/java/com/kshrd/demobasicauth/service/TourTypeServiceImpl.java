package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.Slider;
import com.kshrd.demobasicauth.model.TourType;
import com.kshrd.demobasicauth.repository.TourTypeRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourTypeServiceImpl implements TourTypeService{

    private final TourTypeRepository tourTypeRepository;
    private static final Logger logger = LoggerFactory.getLogger(SliderServiceImpl.class);

    private final Path root = Paths.get("src/main/resources/images/");

    @Override
    public List<TourType> getTourType() {
        return tourTypeRepository.findAll();
    }

    @Override
    public Object getTourTypeById(Long id) {
        TourType tourType = tourTypeRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Tour type not found."));
        return tourType;
    }

    @Override
    public TourType insertTourType(MultipartFile imageFile, String title, String description) throws IOException {

        TourType tourType = new TourType();

        try{
            String fileName = imageFile.getOriginalFilename();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".jpeg") ||
                    fileName.contains(".ong") ||
                    fileName.contains(".webp") ||
                    fileName.contains((".png"))){
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);

                if(!Files.exists(root)){
                    Files.createDirectories(root);
                }
                Files.copy(imageFile.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                tourType.setImage(fileName);
                tourType.setTitle(title);
                tourType.setDescription(description);
                return tourTypeRepository.save(tourType);
            }
            else {
                throw new NotFoundExceptionClass("File not found.");
            }

        }catch (IOException e){
            throw new IOException("File not found!!");
        }
    }

    @Override
    public void deleteTourType(Long id) throws IOException {
        TourType existTour = tourTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Tour Type not found with id " + id));

        String fileName = existTour.getImage();
        if(fileName != null){
            Path imagePath = root.resolve(fileName);
            Files.deleteIfExists(imagePath);
        }

        tourTypeRepository.deleteById(id);
    }

    @Override
    public TourType updateTourType(MultipartFile file, Long id, String title, String description) {
        TourType existTour = tourTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Tour type not found with id " + id));
        existTour.setTitle(title);
        existTour.setDescription(description);

        if (file != null) {
            String fileName = existTour.getImage();
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
                existTour.setImage(fileName);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }
        }
        return tourTypeRepository.save(existTour);
    }
}
