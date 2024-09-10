package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.BlogComment;
import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.repository.BlogCommentRepository;
import com.raphael.philosophy.repository.PostRepository;
import com.raphael.philosophy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentService {

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public BlogComment addComment(Short userId, Short postId, String text) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        BlogComment blogComment = new BlogComment();
        blogComment.setUser(user);
        blogComment.setPost(post);
        blogComment.setText(text);
        return blogCommentRepository.save(blogComment);
    }
    public void removeComment(Integer commentId) {
        blogCommentRepository.deleteById(commentId);
    }

    public List<BlogComment> getCommentsByPostId(Short postId) {
        return blogCommentRepository.findByPostId(postId);
    }

    public List<BlogComment> getCommentsByUserId(Short userId) {
        return blogCommentRepository.findByUserId(userId);
    }
}
