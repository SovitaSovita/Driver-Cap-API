package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.TourType;
import com.kshrd.demobasicauth.model.request.TourTypeRequest;
import com.kshrd.demobasicauth.repository.TourTypeRepository;
import lombok.RequiredArgsConstructor;
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

    private final Path root = Paths.get("src/main/resources/images");

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
    public TourType insertTourType(MultipartFile imageFile, TourTypeRequest request) throws IOException {

        TourType tourType = new TourType();

        try{
            String fileName = imageFile.getOriginalFilename();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".jpeg") ||
                    fileName.contains(".ong") ||
                    fileName.contains((".png"))){
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);

                if(!Files.exists(root)){
                    Files.createDirectories(root);
                }
                Files.copy(imageFile.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                tourType.setImage(fileName);
                tourType.setTitle(request.getTitle());
                tourType.setDescription(request.getDescription());
                return tourTypeRepository.save(tourType);
            }
            else {
                throw new NotFoundExceptionClass("File not found.");
            }

        }catch (IOException e){
            throw new IOException("File not found!!");
        }
    }
}
