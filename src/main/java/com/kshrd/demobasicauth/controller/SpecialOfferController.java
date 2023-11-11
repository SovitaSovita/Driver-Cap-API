package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.SpecialOffer;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.service.SpecialOfferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SpecialOfferController {
    private final SpecialOfferService specialOfferService;


    @GetMapping("/specialOffers")
    public ResponseEntity<ApiResponse<List<SpecialOffer>>> getSpecialOffer(){

        ApiResponse<List<SpecialOffer>> response = ApiResponse.<List<SpecialOffer>>builder()
                .message("Get Successfully.")
                .status(200)
                .payload(specialOfferService.getSpecialOffer())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/specialOffer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SpecialOffer>> insertSpecialOffer(
                                                        @RequestParam String title,
                                                        @RequestParam String price,
                                                        @RequestParam String duration,
                                                        @RequestParam List<String> description,
                                                        @RequestParam List<MultipartFile> imgList) throws IOException {

        ApiResponse<SpecialOffer> response = ApiResponse.<SpecialOffer>builder()
                .message("Inserted Successfully.")
                .status(200)
                .payload(specialOfferService.insertSpecialOffer(title, price, duration, description, imgList))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping( "/updateOffer")
    public ResponseEntity<ApiResponse<SpecialOffer>> updateSpecialOffer(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String price,
            @RequestParam String duration) throws IOException {

        ApiResponse<SpecialOffer> response = ApiResponse.<SpecialOffer>builder()
                .message("Updated Successfully.")
                .status(200)
                .payload(specialOfferService.updateSpecialOffer(id, title, price, duration))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping ("/deleteOffer/{id}")
    public ResponseEntity<ApiResponse<SpecialOffer>> deleteSpecialOffer(@PathVariable Long id) throws IOException {
        specialOfferService.deleteSpecialOffer(id);
        ApiResponse<SpecialOffer> response = ApiResponse.<SpecialOffer>builder()
                .message("Deleted Successfully.")
                .status(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
