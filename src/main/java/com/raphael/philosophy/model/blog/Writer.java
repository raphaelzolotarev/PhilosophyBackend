package com.raphael.philosophy.model.blog;

import com.raphael.philosophy.model.Configurator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "writers")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Byte id;
    private String name;
    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    private String imageUrl = Configurator.DEFAULT_IMG_URL;
}
