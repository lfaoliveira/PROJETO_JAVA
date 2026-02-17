package com.mainApp.users.services;

import com.mainApp.users.models.User;
import com.mainApp.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(Long id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
