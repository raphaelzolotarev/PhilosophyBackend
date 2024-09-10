package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.Like;
import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPostId(Short postId);
    List<Like> findByUserId(Short userId);
}