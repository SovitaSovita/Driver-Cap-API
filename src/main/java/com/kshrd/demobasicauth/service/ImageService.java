package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    public Resource getImagesByFileName(String fileName) throws NotFoundExceptionClass, IOException {
        Path path = Paths.get("src/main/resources/images/"+ fileName);
        System.out.println("path : "+ path);

        //get and response as image
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
