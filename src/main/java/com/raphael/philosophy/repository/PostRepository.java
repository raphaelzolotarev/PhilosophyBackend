package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Short> {
}
