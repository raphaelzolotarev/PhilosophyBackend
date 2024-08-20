package com.raphael.philosophy.model.user;

import com.raphael.philosophy.model.Audit;
import com.raphael.philosophy.model.Configurator;
import com.raphael.philosophy.model.user.enums.PreferredLanguage;
import com.raphael.philosophy.model.user.enums.Privacy;
import com.raphael.philosophy.model.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User extends Audit {

    //NOT NULL INFOS
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
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Character gender;
    @NotNull
    private Byte age;

    //OPTIONAL INFOS
    private String streetName;
    private String streetNbr;
    private String zip;
    private String city;
    private String country;
    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String bio;
    private String profilePhoto = Configurator.DEFAULT_IMG_URL;

    //STATUS
    private boolean verified;
    private boolean onlineStatus;

    //SETTINGS
    private Role role = Role.SUBSCRIBER;
    private Privacy whoCanMessageMe;
    private Privacy whoCanSeeMyInfos;
    private PreferredLanguage preferredLanguage;

    //FRIENDS
    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followers;
    @ManyToMany
    @JoinTable(
            name = "followingList",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> followingList;

    public User(String username, String email, String password, String firstName, String lastName, Character gender, Byte age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }
}
