package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "hotel")
public class HotelEntity extends PersonEntity {


    @Column(name ="hotel_name",nullable = false,unique = true)
     private String name;

    @ManyToOne
    @JoinColumn(name = "region_id",nullable = false)
    private RegionEntity regionId;

    @Column(name = "nuber_stars")
    private Integer numberStars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> roomList;

}
