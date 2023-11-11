package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.PopularTour;
import com.kshrd.demobasicauth.repository.PopularTourRepository;
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
public class PopularTourImpl implements PopularTourService{
    private final PopularTourRepository popularTourRepository;
    private static final Logger logger = LoggerFactory.getLogger(SliderServiceImpl.class);

    private final Path root = Paths.get("src/main/resources/images/");
    @Override
    public List<PopularTour> getPopularTour() {
        return popularTourRepository.findAll();
    }

    @Override
    public Object getPopularTourById(Long id) {
        return popularTourRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("popular tour not found."));
    }

    @Override
    public PopularTour insertPopularTour(MultipartFile imageFile, String title, String duration, String price) throws IOException {
        PopularTour popularTour = new PopularTour();

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
                popularTour.setImageFile(fileName);
                popularTour.setTitle(title);
                popularTour.setDuration(duration);
                popularTour.setPrice(price);

                return popularTourRepository.save(popularTour);
            }
            else {
                throw new NotFoundExceptionClass("File not found.");
            }

        }catch (IOException e){
            throw new IOException("File not found!!");
        }
    }

    @Override
    public PopularTour updatePopularTour(MultipartFile file, Long id, String title, String duration, String price) {
        PopularTour existTour = popularTourRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Tour type not found with id " + id));
        existTour.setTitle(title);
        existTour.setDuration(duration);
        existTour.setPrice(price);

        if (file != null) {
            String fileName = existTour.getImageFile();
            Path imagePath = root.resolve(fileName);

            // Delete the old image file
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }

            System.out.println("file ::" + fileName);

            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
            System.out.println("file afrtwer ::" + fileName);

            try {
                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                existTour.setImageFile(fileName);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }
        }
        return popularTourRepository.save(existTour);
    }

    @Override
    public void deletePopularTour(Long id) throws IOException {
        PopularTour existTour = popularTourRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Tour not found with id " + id));

        String fileName = existTour.getImageFile();
        if(fileName != null){
            Path imagePath = root.resolve(fileName);
            Files.deleteIfExists(imagePath);
        }

        popularTourRepository.deleteById(id);
    }
}
