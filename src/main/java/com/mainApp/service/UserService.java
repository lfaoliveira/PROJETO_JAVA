package com.mainApp.service;

import com.mainApp.model.entity.UserEntity;
import com.mainApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    public UserEntity getById(Long id) {
        return repository.findById(id);
    }

    public UserEntity create(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity update(Long id, UserEntity user) {
        user.setId(id);
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
