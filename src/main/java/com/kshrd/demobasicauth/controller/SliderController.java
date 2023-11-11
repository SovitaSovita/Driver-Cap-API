package com.kshrd.demobasicauth.controller;


import com.kshrd.demobasicauth.model.Slider;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.model.response.NoDataResponse;
import com.kshrd.demobasicauth.service.SliderServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class SliderController {

    private static final Logger logger = LoggerFactory.getLogger(SliderController.class);

    private final SliderServiceImpl sliderService;

    @GetMapping("/sliders")
    public ResponseEntity<?> getSliderInfo() {
        ApiResponse<List<Slider>> response = ApiResponse.<List<Slider>>builder()
                .status(200)
                .message("Successfully.")
                .payload(sliderService.getSlider())
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/sliders/{id}")
    public ResponseEntity<?> getSliderInfoById(@PathVariable Long id) {
        ApiResponse<Slider> response = ApiResponse.<Slider>builder()
                .status(200)
                .message("Get (" + id + ") Successfully.")
                .payload(sliderService.getSliderById(id))
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/slider", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> insertSlider(@RequestParam MultipartFile imageFile, @RequestParam String title) throws IOException {
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .status(200)
                .message("Inserted Successfully.")
                .payload(sliderService.insertSlider(imageFile,title))
                .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping(value = "/updateSlider", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Slider>> updateSlider(@RequestParam(required = false) MultipartFile imageFile,
                                                            @RequestParam Long id,
                                                            @RequestParam String title) {
        ApiResponse<Slider> response = ApiResponse.<Slider>builder()
                .status(200)
                .message("Updated Successfully.")
                .payload(sliderService.updateSlider(imageFile, id, title))
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteSlider/{id}")
    public ResponseEntity<?> deleteSlider(@PathVariable Long id) throws IOException {
        sliderService.deleteSlider(id);
        NoDataResponse response = NoDataResponse.builder()
                .status(200)
                .message("Deleted Successfully.")
                .build();
        return ResponseEntity.ok(response);
    }
}