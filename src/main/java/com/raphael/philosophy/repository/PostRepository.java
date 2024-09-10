package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Short> {
    List<Post> findByAuthorId(Short userId);
    List<Post> findAllByAuthorId(Short userId);
}
