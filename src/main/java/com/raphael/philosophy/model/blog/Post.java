package com.raphael.philosophy.model.blog;

import com.raphael.philosophy.model.Audit;
import com.raphael.philosophy.model.Configurator;
import com.raphael.philosophy.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "posts")
public class Post extends Audit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl = Configurator.DEFAULT_IMG_URL;

    @NotNull
    private String category;

    @NotNull
    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}