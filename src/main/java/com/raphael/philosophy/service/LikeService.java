package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.Like;
import com.raphael.philosophy.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository repo;

    public List<Like> getAllLikes() {
        return repo.findAll();
    }

    public Like addLike(Like like) {
        return repo.save(like);
    }

    public boolean deleteLike(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
