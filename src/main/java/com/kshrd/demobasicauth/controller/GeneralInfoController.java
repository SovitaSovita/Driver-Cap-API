package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.GeneralInfo;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.service.GeneralInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1")

public class GeneralInfoController {

    private final GeneralInfoService generalInfoService;
    @GetMapping("/generalinfo")
    public ResponseEntity<?> getGeneralInfo(){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .message("Get Successfully.")
                .status(200)
                .payload(generalInfoService.getGeneralInfo())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/updateInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<GeneralInfo>> updateSlider(@RequestParam(required = false) MultipartFile imageFile,
                                                              @RequestParam String timeWork,
                                                              @RequestParam String description) {
        ApiResponse<GeneralInfo> response = ApiResponse.<GeneralInfo>builder()
                .status(200)
                .message("Updated Successfully.")
                .payload(generalInfoService.updateGeneralInfo(imageFile, timeWork, description))
                .build();
        return ResponseEntity.ok(response);
    }
}
