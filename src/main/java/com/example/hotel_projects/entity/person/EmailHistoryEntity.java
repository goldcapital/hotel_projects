package com.example.hotel_projects.entity.person;

import com.example.hotel_projects.entity.BastEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity extends BastEntity {

    @Column(name = "message",columnDefinition = "TEXT")
   private String message;
    @Column(name = "email")
   private  String email;
}
