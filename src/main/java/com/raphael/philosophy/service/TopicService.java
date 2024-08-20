package com.raphael.philosophy.service;

import com.raphael.philosophy.model.forum.Topic;
import com.raphael.philosophy.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository repo;

    public List<Topic> getAllTopics() {
        return repo.findAll();
    }

    public Optional<Topic> getTopicById(Short id) {
        return repo.findById(id);
    }

    public Topic createTopic(Topic topic) {
        return repo.save(topic);
    }

    public Optional<Topic> updateTopic(Short id, Topic topic) {
        if (repo.existsById(id)) {
            topic.setId(id);
            return Optional.of(repo.save(topic));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteTopic(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
