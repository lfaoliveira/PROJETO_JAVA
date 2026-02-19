package com.mainApp.repository;

import com.mainApp.model.dto.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void delete(Long id);
}