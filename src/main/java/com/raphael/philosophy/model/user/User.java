package com.raphael.philosophy.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raphael.philosophy.model.Audit;
import com.raphael.philosophy.model.user.enums.Gender;
import com.raphael.philosophy.model.user.enums.PreferredLanguage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User extends Audit implements Serializable {

    //NON NULL INFOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Short id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PreferredLanguage preferredLanguage;

    //PHOTO
    @NotNull
    private String profilePhoto;
    @NotNull
    private String coverPhoto = "assets/images/myprofilecover.jpg";

    //ROLE
    private String role = "USER";

    @NotNull
    @Lob
    @Column(name = "bio", columnDefinition = "LONGTEXT")
    private String bio;

    // FOLLOWERS & FOLLOWING LISTS
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @JsonIgnoreProperties({"followers", "following"})
    private List<User> following = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"followers", "following"})
    private List<User> followers = new ArrayList<>();
}