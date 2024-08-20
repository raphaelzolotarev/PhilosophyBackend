package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.user.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<UserMessage,Short> {
}
