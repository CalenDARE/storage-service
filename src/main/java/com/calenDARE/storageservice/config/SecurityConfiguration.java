package com.calenDARE.storageservice.config;

import com.calenDARE.storageservice.model.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.calenDARE.storageservice.model.Permission.*;
import static com.calenDARE.storageservice.model.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/storage-service/**", "/**")
                .permitAll()
                //.requestMatchers("/storage-service/**")//.hasAnyRole(ADMIN.name(), MANAGER.name())
                //.requestMatchers(GET, "/storage-service/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                //.requestMatchers(POST, "/storage-service/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                //.requestMatchers(PUT, "/storage-service/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                //.requestMatchers(DELETE, "/storage-service/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/storage-service/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
