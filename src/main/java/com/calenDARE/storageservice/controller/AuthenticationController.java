package com.calenDARE.storageservice.controller;

import com.calenDARE.storageservice.model.AuthenticationRequest;
import com.calenDARE.storageservice.model.AuthenticationResponse;
import com.calenDARE.storageservice.model.RegisterRequest;
import com.calenDARE.storageservice.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/storage-service/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
        return ResponseEntity.ok(service.register(request));
        } catch(KeyAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already used.");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
