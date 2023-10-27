package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.TourType;
import com.kshrd.demobasicauth.model.request.TourTypeRequest;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.service.TourTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1")
public class TourTypeController {

    private final TourTypeService tourTypeService;

    @GetMapping("/tours")
    public ResponseEntity<ApiResponse<List<TourType>>> getTourType(){
        ApiResponse<List<TourType>> response = ApiResponse.<List<TourType>>builder()
                .message("Get Successfully.")
                .status(200)
                .payload(tourTypeService.getTourType())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<?> getTourTypeById(@PathVariable Long id){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .message("Get (" + id + ") Successfully.")
                .status(200)
                .payload(tourTypeService.getTourTypeById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/tour", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertTourType(@RequestParam MultipartFile imageFile, @RequestBody TourTypeRequest request) throws IOException {
        ApiResponse<TourType> response = ApiResponse.<TourType>builder()
                .message("Insert Successfully.")
                .status(200)
                .payload(tourTypeService.insertTourType(imageFile, request))
                .build();
        return ResponseEntity.ok(response);
    }
}
