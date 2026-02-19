package com.mainApp.controller;

import com.mainApp.model.entity.UserEntity;
import com.mainApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Long id,
                             @RequestBody UserEntity user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

