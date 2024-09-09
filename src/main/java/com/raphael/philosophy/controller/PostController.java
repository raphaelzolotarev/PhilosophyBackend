package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.blog.Like;
import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.service.BlogCommentService;
import com.raphael.philosophy.service.LikeService;
import com.raphael.philosophy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private BlogCommentService blogCommentService;
    private final PostService service;


    @GetMapping("/show")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = service.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/showrecent")
    public ResponseEntity<List<Post>> getRecent() {
        List<Post> posts = service.getRecent();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Short id) {
        return service.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post newPost = service.createPost(post);
        if(newPost != null)
            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Post>> searchPosts(@PathVariable("keyword") String keyword)  {
        List<Post> posts = service.searchPostWithKeyword(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/searchtag/{keyword}")
    public ResponseEntity<List<Post>> searchPostsByTags(@PathVariable("keyword") String keyword)  {
        List<Post> posts = service.searchPostsByTag(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        Post updatedPost = service.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Short id) {
        service.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //LIKES ADD AND DELETE
    @PostMapping("/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Short postId, @RequestParam Short userId) {
        likeService.addLike(userId, postId);
        return ResponseEntity.ok("Post liked successfully");
    }
    @PostMapping("/dislike/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Short postId, @RequestParam Short userId) {
        likeService.removeLike(userId, postId);
        return ResponseEntity.ok("Post unliked successfully");
    }

    //COMMENTS ADD AND DELETE
    @PostMapping("/comment/{postId}")
    public ResponseEntity<String> commentPost(@PathVariable Short postId, @RequestParam Short userId, @RequestParam String text) {
        blogCommentService.addComment(userId, postId, text);
        return ResponseEntity.ok("Post commented successfully");
    }
    @PostMapping("/uncomment/{commentId}")
    public ResponseEntity<String> uncommentPost(@PathVariable Integer commentId) {
        blogCommentService.removeComment(commentId);
        return ResponseEntity.ok("Post uncommented successfully");
    }

    // GET POSTS BY USER ID
    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Short userId) {
        List<Post> posts = service.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

}
