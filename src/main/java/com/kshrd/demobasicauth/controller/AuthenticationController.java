package com.kshrd.demobasicauth.controller;

import com.kshrd.demobasicauth.jwt.JwtTokenUtil;
import com.kshrd.demobasicauth.jwt.jwtModel.JwtRequest;
import com.kshrd.demobasicauth.jwt.jwtModel.JwtResponse;
import com.kshrd.demobasicauth.model.response.ApiResponse;
import com.kshrd.demobasicauth.model.AppUserDto;
import com.kshrd.demobasicauth.model.request.AppUserRequest;
import com.kshrd.demobasicauth.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(AppUserService appUserService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUserRequest appUserRequest){
        AppUserDto appUserDto = appUserService.register(appUserRequest);
        ApiResponse<AppUserDto> response = ApiResponse.<AppUserDto>builder()
                .status(200)
                .message("success")
                .payload(appUserDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
            authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid email or password");
        }

        final UserDetails userDetails = appUserService
                .loadUserByUsername(jwtRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, LocalDateTime.now(), 200));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
