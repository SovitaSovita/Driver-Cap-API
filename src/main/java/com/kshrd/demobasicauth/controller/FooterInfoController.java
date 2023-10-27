package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.FooterInfo;
import com.kshrd.demobasicauth.model.Slider;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.service.FooterInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
@RequiredArgsConstructor
public class FooterInfoController {
    private final FooterInfoService footerInfoService;

    @GetMapping("/footer")
    public ResponseEntity<ApiResponse<Object>> getFooterInfo(){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .status(200)
                .message("Get Successfully.")
                .payload(footerInfoService.getFooterInfo())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatefooter")
    public ResponseEntity<ApiResponse<Object>> updateFooterInfo(@RequestBody FooterInfo footerInfo){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .status(200)
                .message("Updated Successfully.")
                .payload(footerInfoService.updateFooterInfo(footerInfo))
                .build();
        return ResponseEntity.ok(response);
    }
}
