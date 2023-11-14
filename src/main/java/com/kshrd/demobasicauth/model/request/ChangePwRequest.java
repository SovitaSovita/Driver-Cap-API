package com.kshrd.demobasicauth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePwRequest {
    @NotBlank(message = "Password is required")
    private String currentPassword;
    @NotBlank(message = "Password is required")
    private String newPassword;

    @NotBlank(message = "Password is required")
    private String confirmPassword;
}
