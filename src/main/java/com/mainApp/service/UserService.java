package com.mainApp.service;

import com.mainApp.model.entity.UserEntity;
import com.mainApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    public UserEntity getById(Integer id) {
        Optional<UserEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        } else {
            return entity.get();
        }
    }

    public UserEntity create(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity update(Integer id, UserEntity user) {
        user.setId(id);
        return repository.save(user);
    }

    public void delete(Integer id) {
        UserEntity ent = getById(id); // Will throw if not found
        repository.delete(ent);
    }
}
