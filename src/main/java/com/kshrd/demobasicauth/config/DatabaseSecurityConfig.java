package com.kshrd.demobasicauth.config;

import com.kshrd.demobasicauth.jwt.JwtAuthEntryPoint;
import com.kshrd.demobasicauth.jwt.JwtTokenFilter;
import com.kshrd.demobasicauth.service.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class DatabaseSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    public DatabaseSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService, JwtTokenFilter jwtTokenFilter, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
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
        http.cors();
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/api/v1/sliders",
                                "/api/v1/sliders/**",
                                "/api/v1/tours",
                                "/api/v1/tours/**",
                                "/api/v1/populartours",
                                "/api/v1/populartours/**",
                                "/api/v1/description",
                                "/api/v1/footer",
                                "/api/v1/generalinfo",
                                "/api/v1/specialOffers",
                                "/api/v1/specialOffer/**",
                                "/v3/api-docs/**",
                                "/api/v1/images",
                                "/api/v1/images/**",
                                "/swagger-ui/**",
                                "/swagger/ui.html").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
                // NO POP-UP LOGIN FORM
//                .authenticationEntryPoint((request, response, authException) -> {
////                     response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\""); <<< (((REMOVED)))
//                    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//                });
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedHeaders(
                Arrays.asList(
                        "X-Requested-With",
                        "Origin",
                        "Content-Type",
                        "Accept",
                        "Authorization",
                        "Access-Control-Allow-Credentials",
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Expose-Headers",
                        "Access-Control-Max-Age",
                        "Access-Control-Request-Headers",
                        "Access-Control-Request-Method",
                        "Age",
                        "Allow",
                        "Alternates",
                        "Content-Range",
                        "Content-Disposition",
                        "Content-Description"
                )
        );
        config.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
        );
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
