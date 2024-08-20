package com.raphael.philosophy.repository;

import com.raphael.philosophy.model.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Byte> {
}
