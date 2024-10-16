package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotel")
public class HotelEntity extends PersonEntity {

    @Column(name = "hotel_name", nullable = false, unique = true)
    private String name;

    @Column(name = "region_id",nullable = false)
    private Long regionId;

    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "nuber_stars")
    private Integer numberStars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> roomList;

}
