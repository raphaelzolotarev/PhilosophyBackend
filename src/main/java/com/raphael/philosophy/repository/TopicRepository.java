package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Short> {
}
