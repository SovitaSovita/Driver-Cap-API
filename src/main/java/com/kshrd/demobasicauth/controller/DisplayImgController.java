package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class DisplayImgController {

    private final ImageService imageService;

    @GetMapping("/images")
    public ResponseEntity<?> getImagesByFileName(@RequestParam String fileName) throws NotFoundExceptionClass {
        try{
            Resource file = imageService.getImagesByFileName(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
        }catch (Exception e){
            throw new NotFoundExceptionClass ("this file isn't exist.");
        }
    }
}
