package com.raphael.philosophy.service;

import com.raphael.philosophy.model.forum.ForumComment;
import com.raphael.philosophy.repository.ForumCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumCommentService {
    private final ForumCommentRepository repo;

    public List<ForumComment> getAllForumComments() {
        return repo.findAll();
    }

    public Optional<ForumComment> getForumCommentById(Short id) {
        return repo.findById(id);
    }

    public ForumComment createForumComment(ForumComment forumComment) {
        return repo.save(forumComment);
    }

    public Optional<ForumComment> updateForumComment(Short id, ForumComment forumComment) {
        if (repo.existsById(id)) {
            forumComment.setId(id);
            return Optional.of(repo.save(forumComment));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteForumComment(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
