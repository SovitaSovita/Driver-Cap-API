package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.model.EachDescription;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.service.EachDescriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1")
public class EachDescriptionController {

    private final EachDescriptionService eachDescriptionService;

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .status(200)
                .message("Get Successfully.")
                .payload(eachDescriptionService.getDescription())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatedescription")
    public ResponseEntity<?> updateDescription(@RequestBody EachDescription description){
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .status(200)
                .message("Updated Successfully.")
                .payload(eachDescriptionService.updateDescrition(description))
                .build();
        return ResponseEntity.ok(response);
    }
}
