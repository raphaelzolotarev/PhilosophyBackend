package com.raphael.philosophy.model.user;

import com.raphael.philosophy.model.Audit;
import com.raphael.philosophy.model.Configurator;
import com.raphael.philosophy.model.user.enums.PreferredLanguage;
import com.raphael.philosophy.model.user.enums.Privacy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    //PHOTO
    private String profilePhoto = Configurator.DEFAULT_IMG_URL;

    //ROLE
    private String role = "USER";

    //LANG
    @Enumerated(EnumType.STRING)
    private PreferredLanguage preferredLanguage = PreferredLanguage.EN;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
/*
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
    private String role = "USER";
    @Enumerated(EnumType.STRING)
    private Privacy whoCanMessageMe = Privacy.EVERYONE;
    @Enumerated(EnumType.STRING)
    private Privacy whoCanSeeMyInfos = Privacy.EVERYONE;
    @Enumerated(EnumType.STRING)
    private PreferredLanguage preferredLanguage = PreferredLanguage.EN;

    //FRIENDS
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> followers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
}*/
