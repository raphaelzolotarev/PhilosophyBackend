package com.raphael.philosophy.model.blog;

import com.raphael.philosophy.model.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "blog_comments")
public class BlogComment extends Message {
    @ManyToOne
    @JoinColumn(name = "id_post", referencedColumnName = "id")
    private Post post;
}

