package com.example.jwt.d_persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.jwt.c_domain.models.User;
import com.example.jwt.c_domain.repository.UserRepository;
import com.example.jwt.d_persistence.jpa.interfaces.UserJpa;
import com.example.jwt.d_persistence.jpa.mappers.UserEntityMapper;
import com.example.jwt.d_persistence.jpa.models.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpa userJpa;

    @Override
    public List<User> findAll() {
        return userJpa.findAll().stream()
                .map(userEntity -> UserEntityMapper.toUser(userEntity))
                .toList();
    }

    @Override
    public Optional<User> findById(String id) {
        return userJpa.findById(id)
                .map(userEntity -> UserEntityMapper.toUser(userEntity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpa.findByEmail(email)
                .map(userEntity -> UserEntityMapper.toUser(userEntity));
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntityMapper.toUserEntity(user);

        return UserEntityMapper.toUser(userJpa.save(userEntity));
    }

    @Override
    public void delete(String id) {
        userJpa.deleteById(id);
    }
}