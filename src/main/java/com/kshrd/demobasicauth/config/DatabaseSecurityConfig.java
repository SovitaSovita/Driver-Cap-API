package com.kshrd.demobasicauth.config;

import com.kshrd.demobasicauth.jwt.JwtTokenFilter;
import com.kshrd.demobasicauth.service.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class DatabaseSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;
    private final JwtTokenFilter jwtTokenFilter;

    public DatabaseSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService, JwtTokenFilter jwtTokenFilter) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //<< implementing this interface
        http
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/v3/api-docs/**",
                                "/api/v1/images",
                                "/api/v1/images/**",
                                "/swagger-ui/**",
                                "/swagger/ui.html").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
                // NO POP-UP LOGIN FORM
//                .authenticationEntryPoint((request, response, authException) -> {
////                     response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\""); <<< (((REMOVED)))
//                    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//                });
        return http.build();
    }
}
