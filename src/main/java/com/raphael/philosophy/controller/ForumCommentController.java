package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.forum.ForumComment;
import com.raphael.philosophy.service.ForumCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum-comments")
@RequiredArgsConstructor
public class ForumCommentController {
    private final ForumCommentService service;

    @GetMapping
    public List<ForumComment> getAllForumComments() {
        return service.getAllForumComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumComment> getForumCommentById(@PathVariable Short id) {
        return service.getForumCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ForumComment createForumComment(@RequestBody ForumComment forumComment) {
        return service.createForumComment(forumComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumComment> updateForumComment(@PathVariable Short id, @RequestBody ForumComment forumComment) {
        return service.updateForumComment(id, forumComment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumComment(@PathVariable Short id) {
        if (service.deleteForumComment(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
