package com.example.hotel_projects.entity;



import com.example.hotel_projects.enums.ProfileRole;
import com.example.hotel_projects.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "profile")
public class ProfileEntity extends BastEntity{

    @Column(name = "email")
    private  String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
     private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "is_logged_in")
    private boolean isLoggedIn;
}
