package com.mainApp.repository;

import com.mainApp.model.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();
    private long counter = 1;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User findById(Long id) {
        return store.get(id);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(counter++);
        }
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
}