package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.forum.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCommentRepository extends JpaRepository<ForumComment,Short> {
}
