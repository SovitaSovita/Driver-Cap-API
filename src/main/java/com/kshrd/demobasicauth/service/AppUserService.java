package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.AppUser;
import com.kshrd.demobasicauth.model.AppUserDto;
import com.kshrd.demobasicauth.model.request.AppUserRequest;
import com.kshrd.demobasicauth.model.request.ChangePwRequest;
import com.kshrd.demobasicauth.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final ModelMapper mapper = new ModelMapper();

    public AppUserService(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(username);
        if(appUser == null){
            throw new NotFoundExceptionClass("User not found.");
        }
        return appUser;
    }

    public AppUserDto register(AppUserRequest appUserRequest) {
        if (userEmailExists(appUserRequest.getEmail())) {
            throw new NotFoundExceptionClass("Email is already in use.");
        }
        //set pw to encoder
        String pass = passwordEncoder.encode(appUserRequest.getPassword());
        //set pw that we already encode to appUserRequest
        appUserRequest.setPassword(pass);
        AppUser appUser = mapper.map(appUserRequest, AppUser.class);
        appUserRepository.save(appUser);
        //return as userDTO
        return mapper.map(appUser, AppUserDto.class);
    }
    private boolean userEmailExists(String email) {
        return appUserRepository.existsByEmail(email);
    }

    public void changePassword(Long id, ChangePwRequest password) {
        String pass = passwordEncoder.encode(password.getCurrentPassword());
        String newpass = passwordEncoder.encode(password.getNewPassword());

        AppUser getOldPw = appUserRepository.getAppUserById(id);

        if (!passwordEncoder.matches(password.getCurrentPassword(), getOldPw.getPassword())) {
            throw new IllegalArgumentException("Current Password isn't correct. Please Try Again.");
        }
        if (passwordEncoder.matches(password.getNewPassword(), getOldPw.getPassword())){
            throw new IllegalArgumentException("your new password is still the same with your old password");
        }
        if (!password.getNewPassword().equals(password.getConfirmPassword())) {
            throw new IllegalArgumentException("your confirm password is not match with your new password");
        }
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("User with id " + id + " not found"));
        appUser.setPassword(newpass);
        appUserRepository.save(appUser);
    }
}
