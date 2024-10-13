package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "region")
public class RegionEntity  extends PersonEntity {

    @Column(name = "name",nullable = false)
    private String name;


}
