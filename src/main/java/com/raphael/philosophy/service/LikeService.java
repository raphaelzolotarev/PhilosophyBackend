package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.Like;
import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.repository.LikeRepository;
import com.raphael.philosophy.repository.PostRepository;
import com.raphael.philosophy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public Like addLike(Short userId, Short postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Vérifier si le like existe déjà
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            throw new RuntimeException("User already liked this post");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }

    public void removeLike(Short userId, Short postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepository.delete(like);
    }


    public List<Like> getLikesByPostId(Short postId) {
        return likeRepository.findByPostId(postId);
    }

    public List<Like> getLikesByUserId(Short userId) {
        return likeRepository.findByUserId(userId);
    }
}

