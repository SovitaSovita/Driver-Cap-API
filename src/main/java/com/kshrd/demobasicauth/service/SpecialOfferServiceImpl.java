package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.SpecialOffer;
import com.kshrd.demobasicauth.model.SpecialOfferImage;
import com.kshrd.demobasicauth.repository.SpecialOfferImageRepository;
import com.kshrd.demobasicauth.repository.SpecialOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements SpecialOfferService {
    private final SpecialOfferRepository specialOfferRepository;
    private final SpecialOfferImageRepository specialOfferImageRepository;
    private final Path root = Paths.get("src/main/resources/images");


    @Override
    public List<SpecialOffer> getSpecialOffer() {
        return specialOfferRepository.findAll();
    }

    @Override
    public SpecialOffer insertSpecialOffer(String title, String price, String duration, List<String> description, List<MultipartFile> imgList) throws IOException {

        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setTitle(title);
        specialOffer.setPrice(price);
        specialOffer.setDuration(duration);

        List<String> imagePaths = saveImagesToFileSystem(imgList);

        List<SpecialOfferImage> imageMetadataList = new ArrayList<>();
        for (String imagePath : imagePaths) {
            SpecialOfferImage metadata = new SpecialOfferImage();
            metadata.setFileName(getFileNameFromPath(imagePath));
            // Extract the file name
            for (String desc : description) {
                metadata.setDescription(desc);
            }
            metadata.setSpecialOffer(specialOffer);
            imageMetadataList.add(metadata);
        }

        specialOffer.setImgList(imageMetadataList);

        return specialOfferRepository.save(specialOffer);
    }

    @Override
    public void deleteSpecialOffer(Long id) throws IOException {
        SpecialOffer specialOffer = specialOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("SpecialOffer not found with id: " + id));

        // Delete associated images from the file system
        deleteImagesFromFileSystem(specialOffer.getImgList());

        // Delete the SpecialOffer entity
        specialOfferRepository.deleteById(id);
    }

    @Override
    public SpecialOffer updateSpecialOffer(Long id, String title, String price, String duration) throws IOException {
        SpecialOffer specialOffer = specialOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("SpecialOffer not found with id: " + id));

        //delete
        //deleteImagesFromFileSystem(specialOffer.getImgList());
        //specialOfferRepository.deleteById(id);
        specialOffer.setImgList(specialOffer.getImgList());

        //set new value
        specialOffer.setTitle(title);
        specialOffer.setPrice(price);
        specialOffer.setDuration(duration);

        return specialOfferRepository.save(specialOffer);
    }

    private List<String> saveImagesToFileSystem(List<MultipartFile> imgList) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile imgFile : imgList) {
            if (!imgFile.isEmpty()) {
                try {
                    // Generate a unique filename
                    String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(imgFile.getOriginalFilename());

                    // Create the full path to save the image
                    String filePath = root + File.separator + fileName;

                    // Save the image to the specified directory
                    Files.copy(imgFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                    // Add the image path to the list
                    imagePaths.add(filePath);
                } catch (IOException e) {
                    // Handle the exception
                    throw new IOException("Failed to save one or more images to the file system.");
                }
            }
        }
        return imagePaths;
    }

    private String getFileNameFromPath(String filePath) {
        // Extract the file name from the full path (e.g., /path/to/uploaded/file.jpg -> file.jpg)
        return new File(filePath).getName();
    }

    private void deleteImagesFromFileSystem(List<SpecialOfferImage> imageList) throws IOException {
        for (SpecialOfferImage image : imageList) {
            try {
                Path imagePath = root.resolve(image.getFileName());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                // Handle the exception
                throw new IOException("Failed to delete one or more images from the file system.");
            }
        }
    }
}




