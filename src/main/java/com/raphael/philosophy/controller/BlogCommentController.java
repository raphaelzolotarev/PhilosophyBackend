package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.blog.BlogComment;
import com.raphael.philosophy.service.BlogCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-comments")
@RequiredArgsConstructor
public class BlogCommentController {
    private final BlogCommentService service;

    @GetMapping
    public List<BlogComment> getAllBlogComments() {
        return service.getAllBlogComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogComment> getBlogCommentById(@PathVariable Short id) {
        return service.getBlogCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BlogComment createBlogComment(@RequestBody BlogComment blogComment) {
        return service.createBlogComment(blogComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogComment> updateBlogComment(@PathVariable Short id, @RequestBody BlogComment blogComment) {
        return service.updateBlogComment(id, blogComment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogComment(@PathVariable Short id) {
        if (service.deleteBlogComment(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}