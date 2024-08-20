package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.blog.Like;
import com.raphael.philosophy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService service;

    @GetMapping
    public List<Like> getAllLikes() {
        return service.getAllLikes();
    }

    @PostMapping
    public Like addLike(@RequestBody Like like) {
        return service.addLike(like);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Integer id) {
        if (service.deleteLike(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
