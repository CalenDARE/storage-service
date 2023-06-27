package com.calenDARE.storageservice.controller;

import com.calenDARE.storageservice.dao.UserRepository;
import com.calenDARE.storageservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/storage-service")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAll() {
        try {
            if (userRepository.findAll().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                User updatedUserData = userData.get();
                updatedUserData.setFirstName(user.getFirstName());
                updatedUserData.setLastName(user.getLastName());
                updatedUserData.setEmail(user.getEmail());
                updatedUserData.setPassword(user.getPassword());

                return new ResponseEntity<>(userRepository.save(updatedUserData), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
    @PostMapping("/user/{user}/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String username = user.getEmail();
        String password = user.getPassword();

        if (isValidCredentials(username, password)) {
            String authToken = generateAuthToken();

            return new ResponseEntity<>(authToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isValidCredentials(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user.getPassword().equals(password);
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    */
}
