package com.raphael.philosophy.service;

import com.raphael.philosophy.model.blog.Tag;
import com.raphael.philosophy.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository repo;

    public List<Tag> getAllTags() {
        return repo.findAll();
    }

    public Optional<Tag> getTagById(Byte id) {
        return repo.findById(id);
    }

    public Tag createTag(Tag tag) {
        return repo.save(tag);
    }

    public Optional<Tag> updateTag(Byte id, Tag tag) {
        if (repo.existsById(id)) {
            tag.setId(id);
            return Optional.of(repo.save(tag));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteTag(Byte id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
