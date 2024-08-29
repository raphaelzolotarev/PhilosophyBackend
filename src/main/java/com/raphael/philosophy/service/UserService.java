package com.raphael.philosophy.service;

import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public Optional<User> getUserById(Short id) {
        return repo.findById(id);
    }

    public User getUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public List<User> searchUserWithKeyword(String keyword){
        return repo.findAll()
                .stream()
                .filter(user -> user.getUsername().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public void deleteUser(Short id) {
        repo.deleteById(id);
    }
}
