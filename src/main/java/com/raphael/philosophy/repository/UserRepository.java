package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Short> {
    public User findByUsername(String username);
}
