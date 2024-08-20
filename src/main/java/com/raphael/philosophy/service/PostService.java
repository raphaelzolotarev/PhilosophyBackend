package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repo;

    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    public Optional<Post> getPostById(Short id) {
        return repo.findById(id);
    }

    public Post createPost(Post post) {
        return repo.save(post);
    }

    public Optional<Post> updatePost(Short id, Post post) {
        if (repo.existsById(id)) {
            post.setId(id);
            return Optional.of(repo.save(post));
        } else {
            return Optional.empty();
        }
    }

    public boolean deletePost(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
