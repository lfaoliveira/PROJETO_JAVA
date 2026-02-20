package com.mainApp.controller;

import com.mainApp.model.entity.UserEntity;
import com.mainApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable Integer id) {
        try {
            UserEntity byId = service.getById(id);
            log.info("User " + id + ": " + byId);
            return byId;

        } catch (Exception e) {
            System.out.println("Error fetching user with id " + id + ": " + e.getMessage());
            throw e; // Re-throw to let Spring handle the error response
        }
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Integer id,
                             @RequestBody UserEntity user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

