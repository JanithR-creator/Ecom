package com.example.ecom.feanix.security;

import com.example.ecom.feanix.jwt.JwtConfig;
import com.example.ecom.feanix.jwt.JwtTokenVerifier;
import com.example.ecom.feanix.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.example.ecom.feanix.service.impl.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import javax.crypto.SecretKey;
import java.util.List;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {
    private final JwtConfig jwtConfig;
    private final ApplicationUserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(JwtConfig jwtConfig,
                                     ApplicationUserServiceImpl userService,
                                     PasswordEncoder passwordEncoder,
                                     SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));//any origin allowed but if you want to allow something unique can specify
        corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "GET", "DELETE", "OPTION", "PATCH"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> corsConfiguration))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager, jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/products/visitor/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}
