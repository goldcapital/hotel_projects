package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.ProfileEntity;
import com.example.hotel_projects.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,String> {

    Optional<ProfileEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete  from ProfileEntity  where email=?1")
    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status= :status where email= :email")
    void updateByEmail(@Param("status") ProfileStatus profileStatus, @Param( "email") String email);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set isLoggedIn= :isLoggedIn where email= :email")
    void updateByLoggedInAndEmail(@Param("isLoggedIn") Boolean b, @Param("email")String email);

}
