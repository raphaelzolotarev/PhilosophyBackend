package com.raphael.philosophy.service;

import com.raphael.philosophy.model.user.UserMessage;
import com.raphael.philosophy.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repo;

    public List<UserMessage> getAllMessages() {
        return repo.findAll();
    }

    public Optional<UserMessage> getMessageById(Short id) {
        return repo.findById(id);
    }

    public UserMessage createMessage(UserMessage message) {
        return repo.save(message);
    }

    public Optional<UserMessage> updateMessage(Short id, UserMessage message) {
        if (repo.existsById(id)) {
            message.setId(id);
            return Optional.of(repo.save(message));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteMessage(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
