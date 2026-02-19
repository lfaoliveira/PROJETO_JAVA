package com.mainApp.repository;

import com.mainApp.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepository implements UserRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAll() {
        return entityManager
                .createQuery("from UserEntity", UserEntity.class)
                .getResultList();
    }

    @Override
    public UserEntity findById(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    public void delete(Long id) {
        UserEntity user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
