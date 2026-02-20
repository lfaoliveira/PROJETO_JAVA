package com.mainApp.repository;

import com.mainApp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Save, findById, findAll, and delete are all provided automatically!
}