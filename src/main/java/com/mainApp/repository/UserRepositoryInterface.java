package com.mainApp.repository;

import com.mainApp.model.entity.UserEntity;

import java.util.List;

public interface UserRepositoryInterface {

    List<UserEntity> findAll();

    UserEntity findById(Long id);

    UserEntity save(UserEntity user);

    void delete(Long id);
}