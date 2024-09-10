package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.repository.BlogCommentRepository;
import com.raphael.philosophy.repository.LikeRepository;
import com.raphael.philosophy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repo;
    private final LikeRepository likeRepository;
    private final BlogCommentRepository blogCommentRepository;

    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    public List<Post> getRecent() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Post::getId).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Post> getPostById(Short id) {
        return repo.findById(id);
    }

    public Post createPost(Post post) {
        return repo.save(post);
    }

    public Post updatePost(Post postChanges) {
        Post existingPost = repo.getReferenceById(postChanges.getId());

        //make changes
        existingPost.setTitle(postChanges.getTitle());
        existingPost.setCategory(postChanges.getCategory());
        existingPost.setImageUrl(postChanges.getImageUrl());
        existingPost.setDescription(postChanges.getDescription());

        return repo.save(existingPost);
    }

    public boolean deletePost(Short id) {
        likeRepository.deleteAll(likeRepository.findByPostId(id));
        blogCommentRepository.deleteAll(blogCommentRepository.findByPostId(id));
        repo.deleteById(id);
        return true;
    }

    public List<Post> searchPostWithKeyword(String keyword){
        return repo.findAll()
                .stream()
                .filter(post -> post.getTitle().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Post> searchPostsByTag(String keyword){
        return repo.findAll()
                .stream()
                .filter(post -> post.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByUserId(Short userId) {
        return repo.findByAuthorId(userId);
    }

}