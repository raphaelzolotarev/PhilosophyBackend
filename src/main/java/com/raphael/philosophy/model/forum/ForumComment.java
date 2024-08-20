package com.raphael.philosophy.model.forum;

import com.raphael.philosophy.model.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "forum_comments")
public class ForumComment extends Message {
    @ManyToOne
    @JoinColumn(name = "id_topic", referencedColumnName = "id")
    private Topic topic;
}

