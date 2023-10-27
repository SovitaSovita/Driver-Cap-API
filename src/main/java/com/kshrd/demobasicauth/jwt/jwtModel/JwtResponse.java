package com.kshrd.demobasicauth.jwt.jwtModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private LocalDateTime dateTime = LocalDateTime.now();
    private Integer status;
}
