package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.BlogComment;
import com.raphael.philosophy.repository.BlogCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogCommentService {
    private final BlogCommentRepository repo;

    public List<BlogComment> getAllBlogComments() {
        return repo.findAll();
    }

    public Optional<BlogComment> getBlogCommentById(Short id) {
        return repo.findById(id);
    }

    public BlogComment createBlogComment(BlogComment blogComment) {
        return repo.save(blogComment);
    }

    public Optional<BlogComment> updateBlogComment(Short id, BlogComment blogComment) {
        if (repo.existsById(id)) {
            blogComment.setId(id);
            return Optional.of(repo.save(blogComment));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteBlogComment(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
