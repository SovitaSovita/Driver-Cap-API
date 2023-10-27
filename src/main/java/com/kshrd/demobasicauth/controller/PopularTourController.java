package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.PopularTour;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.model.response.NoDataResponse;
import com.kshrd.demobasicauth.service.PopularTourService;
import com.kshrd.demobasicauth.service.PopularTourService;
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
public class PopularTourController {

    private final PopularTourService popularTourService;

    @GetMapping("/populartours")
    public ResponseEntity<ApiResponse<List<PopularTour>>> getPopularTour(){
        ApiResponse<List<PopularTour>> response = ApiResponse.<List<PopularTour>>builder()
                .message("Get Successfully.")
                .status(200)
                .payload(popularTourService.getPopularTour())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/populartours/{id}")
    public ResponseEntity<?> getPopularTourById(@PathVariable Long id){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .message("Get (" + id + ") Successfully.")
                .status(200)
                .payload(popularTourService.getPopularTourById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/populartours", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertPopularTour(@RequestParam MultipartFile imageFile,
                                            @RequestParam String title,
                                            @RequestParam String duration,
                                               @RequestParam String price) throws IOException {
        ApiResponse<PopularTour> response = ApiResponse.<PopularTour>builder()
                .message("Insert Successfully.")
                .status(200)
                .payload(popularTourService.insertPopularTour(imageFile, title, duration, price))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/updatepopulartours", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PopularTour>> updateSlider(@RequestParam MultipartFile imageFile,
                                                            @RequestParam Long id,
                                                            @RequestParam String title,
                                                            @RequestParam String duration,
                                                                 @RequestParam String price) {
        ApiResponse<PopularTour> response = ApiResponse.<PopularTour>builder()
                .status(200)
                .message("Updated Successfully.")
                .payload(popularTourService.updatePopularTour(imageFile, id, title, duration, price))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletepopulartours/{id}")
    public ResponseEntity<?> deletePopularTour(@PathVariable Long id) throws IOException {
        popularTourService.deletePopularTour(id);
        NoDataResponse response = NoDataResponse.builder()
                .status(200)
                .message("Deleted Successfully.")
                .build();
        return ResponseEntity.ok(response);
    }
}
