package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.BlogComment;
import com.raphael.philosophy.model.blog.Post;
import com.raphael.philosophy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    List<BlogComment> findByPostId(Short postId);
    List<BlogComment> findByUserId(Short userId);
}
