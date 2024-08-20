package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment,Short> {
}
