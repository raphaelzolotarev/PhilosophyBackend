package com.raphael.philosophy.controller;

import com.raphael.philosophy.model.user.UserMessage;
import com.raphael.philosophy.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user-messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    @GetMapping
    public List<UserMessage> getAllMessages() {
        return service.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMessage> getMessageById(@PathVariable Short id) {
        return service.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserMessage createMessage(@RequestBody UserMessage message) {
        return service.createMessage(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMessage> updateMessage(@PathVariable Short id, @RequestBody UserMessage message) {
        return service.updateMessage(id, message)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Short id) {
        if (service.deleteMessage(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}