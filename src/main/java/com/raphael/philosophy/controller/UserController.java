package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.service.JWTService;
import com.raphael.philosophy.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @GetMapping("/show")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

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

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<User>> searchEmployees(@PathVariable("keyword") String keyword)  {
        List<User> employees = service.searchUserWithKeyword(keyword);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

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

    //check if user is connected
    @GetMapping("/auth")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>(service.getUserByUsername(authentication.getName()), HttpStatus.OK);

        } else {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }


    //user login
    @PostMapping("/signin")
    /*public ResponseEntity<User> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);
            //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authenticationToken));
            return new ResponseEntity<>(service.getUserByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }*/
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
            System.out.println("1");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            System.out.println("2");
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            System.out.println("3");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("4");
            // Generate JWT token
            String jwtToken = jwtService.generateToken(authentication);

            System.out.println("5");
            // Create a response map with the token
            Map<String, String> response = new HashMap<>();

            System.out.println("6");
            response.put("token", jwtToken);

            System.out.println("7");
           // return new ResponseEntity<>(response, HttpStatus.OK); //
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }



}
