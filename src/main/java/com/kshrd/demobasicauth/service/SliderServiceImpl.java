package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.controller.SliderController;
import com.kshrd.demobasicauth.exception.AlreadyExistException;
import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.Slider;
import com.kshrd.demobasicauth.model.request.SliderRequest;
import com.kshrd.demobasicauth.repository.SliderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.modelmapper.ModelMapper;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SliderServiceImpl implements SliderService{

    private static final Logger logger = LoggerFactory.getLogger(SliderServiceImpl.class);
    private final SliderRepository sliderRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final Path root = Paths.get("src/main/resources/images");

    @Override
    public List<Slider> getSlider() {
        return sliderRepository.findAll();
    }

    @Override
    public Object insertSlider(MultipartFile file, String title) throws IOException {

        Slider existingSlider = sliderRepository.findByTitle(title);

        if (existingSlider != null) {
            throw new AlreadyExistException("Slider with the title already exists.");
        }

        Slider slider = new Slider();
        slider.setTitle(title);

        try{
            // get file name , ex : sokhen.png
            String fileName = file.getOriginalFilename();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".jpeg") ||
                    fileName.contains(".ong") ||
                    fileName.contains((".png")) ||
                    fileName.contains((".webp"))
            ){
                fileName = UUID.randomUUID() +  "." + StringUtils.getFilenameExtension(fileName);

                // create directory if not exist
                if (!Files.exists(root)){
                    Files.createDirectories(root);
                }
                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                slider.setImage(fileName);
                return sliderRepository.save(slider);
            }else {
                throw new NotFoundExceptionClass("File not found!!");
            }
        }catch (IOException e){
            throw new IOException("File not found!!");
        }
    }

    @Override
    public Slider updateSlider(MultipartFile file, Long id, String title) {
        Slider existSlider = sliderRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Slider not found with id " + id));
        existSlider.setTitle(title);

        if (file != null && !file.isEmpty()) {
            String fileName = existSlider.getImage();
            Path imagePath = root.resolve(fileName);

            // Delete the old image file
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }

            // Generate a new unique filename and copy the new image to the directory
            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
            try {
                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                existSlider.setImage(fileName);
            } catch (IOException e) {
                logger.error("An IOException occurred: ", e.getMessage());
            }
        }
        return sliderRepository.save(existSlider);
    }

    @Override
    public void deleteSlider(Long id) throws IOException {

        Slider existSlider = sliderRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Slider not found with id " + id));

        String fileName = existSlider.getImage();
        if(fileName != null){
            Path imagePath = root.resolve(fileName);
            Files.deleteIfExists(imagePath);
        }
        sliderRepository.deleteById(id);
    }

    @Override
    public Slider getSliderById(Long id) {
        return sliderRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Slider not found."));
    }
}
