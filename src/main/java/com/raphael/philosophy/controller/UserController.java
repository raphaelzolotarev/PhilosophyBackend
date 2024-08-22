package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/show")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /*
    *     @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }*/

    /*@GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Short id) {
        User user = service.getUserById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Short id) {
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = service.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /*@PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }*/

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = service.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Short id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
