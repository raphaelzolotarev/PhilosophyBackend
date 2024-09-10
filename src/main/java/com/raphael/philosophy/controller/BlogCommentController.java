package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.blog.BlogComment;
import com.raphael.philosophy.service.BlogCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    // GET COMMENTS BY POST ID
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<BlogComment>> getCommentsByPostId(@PathVariable Short postId) {
        List<BlogComment> blogComments = blogCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(blogComments);
    }

    // GET COMMENTS BY USER ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogComment>> getCommentsByUserId(@PathVariable Short userId) {
        List<BlogComment> blogComments = blogCommentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(blogComments);
    }
}