package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.service.JWTService;
import com.raphael.philosophy.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.*;

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
        if(newUser != null)
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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

    //user login
    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtService.generateToken(authentication);

            Map<String, String> response = new HashMap<>();

            response.put("token", jwtToken);
            System.out.println(jwtToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/verify-token")
    public ResponseEntity<User> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            username = jwtService.getUsernameFromToken(token);

            if (username != null && jwtService.validateToken(token)) {

                UserDetails userDetails = jwtService.loadUserByUsername(username);

                return ResponseEntity.ok(service.getUserByUsername(userDetails.getUsername()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    //change lang
    @PostMapping("/changelang")
    public ResponseEntity<User> changeUserLang(@RequestParam String username, @RequestParam String lang) {
        System.out.println("controlleur change lang");
        System.out.println(username);
        System.out.println(lang);
        User updatedUser = service.changeUserLang(username, lang);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    //follow a user
    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<User> followUser(@PathVariable Short userId, @PathVariable Short targetUserId) {
        User updatedUser = service.followUser(userId, targetUserId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @PostMapping("/{userId}/unfollow/{targetUserId}")
    public ResponseEntity<User> unFollowUser(@PathVariable Short userId, @PathVariable Short targetUserId) {
        User updatedUser = service.unFollowUser(userId, targetUserId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Short userId) {
        List<User> followers = service.getUserFollowers(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Short userId) {
        List<User> following = service.getUserFollowing(userId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }



}
