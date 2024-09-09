package com.raphael.philosophy.service;

import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.model.user.enums.PreferredLanguage;
import com.raphael.philosophy.repository.BlogCommentRepository;
import com.raphael.philosophy.repository.LikeRepository;
import com.raphael.philosophy.repository.PostRepository;
import com.raphael.philosophy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final LikeRepository likeRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final PostRepository postRepository;

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
        List<User> allUsers = getAllUsers();
        for (User existingUser : allUsers) {
            if (existingUser.getUsername().equals(user.getUsername()) ||
                    existingUser.getEmail().equals(user.getEmail())) {
                return null;
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setProfilePhoto(user.getGender().toString().equals("M") ? "assets/images/male.jpg" :  "assets/images/female.jpg");

        if(user.getPreferredLanguage().toString().equals("EN"))
            user.setBio("Hi everyone i'm here to discover more about spirituality!");
        if(user.getPreferredLanguage().toString().equals("FR"))
            user.setBio("Salut tout le monde, je suis ici pour en apprendre davantage sur la spiritualité !");
        if(user.getPreferredLanguage().toString().equals("NL"))
            user.setBio("Hoi allemaal, ik ben hier om meer te ontdekken over spiritualiteit!");

        return repo.save(user);
    }

    public List<User> searchUserWithKeyword(String keyword){
        return repo.findAll()
                .stream()
                .filter(user -> user.getUsername().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    public User updateUser(User userChanges) {
        User existingUser = repo.getReferenceById(userChanges.getId());

        //make changes
        existingUser.setUsername(userChanges.getUsername());
        existingUser.setEmail(userChanges.getEmail());
        existingUser.setGender(userChanges.getGender());
        existingUser.setProfilePhoto(userChanges.getProfilePhoto());
        existingUser.setCoverPhoto(userChanges.getCoverPhoto());
        existingUser.setBio(userChanges.getBio());

        if(userChanges.getPassword() != "" && userChanges.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(userChanges.getPassword()));

        return repo.save(existingUser);
    }

    public void deleteUser(Short id) {
        likeRepository.deleteAll(likeRepository.findByUserId(id));
        blogCommentRepository.deleteAll(blogCommentRepository.findByUserId(id));
        postRepository.deleteAll(postRepository.findAllByAuthorId(id));
        repo.deleteById(id);
    }

    public User changeUserLang(String username, String lang){
        System.out.println("service");
        System.out.println(lang);
        User user = repo.findByUsername(username);
        if (lang.equalsIgnoreCase("EN")) user.setPreferredLanguage(PreferredLanguage.EN);
        else if (lang.equalsIgnoreCase("FR"))  user.setPreferredLanguage(PreferredLanguage.FR);
        else user.setPreferredLanguage(PreferredLanguage.NL);
        return repo.save(user);
    }

    //follow user
    public User followUser(Short userId, Short targetUserId) {
        User currentUser = repo.getReferenceById(userId);
        User targetUser = repo.getReferenceById(targetUserId);
        currentUser.getFollowing().add(targetUser);
        targetUser.getFollowers().add(currentUser);
        repo.save(currentUser);
        repo.save(targetUser);
        return currentUser;
    }
    public User unFollowUser(Short userId, Short targetUserId) {
        User currentUser = repo.getReferenceById(userId);
        User targetUser = repo.getReferenceById(targetUserId);
        currentUser.getFollowing().remove(targetUser);
        targetUser.getFollowers().remove(currentUser);
        repo.save(currentUser);
        repo.save(targetUser);
        return currentUser;
    }

    public List<User> getUserFollowers(Short userId){
        User user = repo.getReferenceById(userId);
        return user.getFollowers();
    }
    public List<User> getUserFollowing(Short userId){
        User user = repo.getReferenceById(userId);
        return user.getFollowing();
    }
}
