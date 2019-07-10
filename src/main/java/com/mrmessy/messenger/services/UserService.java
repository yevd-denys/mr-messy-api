package com.mrmessy.messenger.services;


import com.mrmessy.messenger.entities.User;
import com.mrmessy.messenger.graphql.dto.UserMutationDto;
import com.mrmessy.messenger.mappers.UserMapper;
import com.mrmessy.messenger.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getById(Integer id) {
        return userRepo.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User save(UserMutationDto dto) {
        var newUser = UserMapper.convert(dto);
        return userRepo.save(newUser);
    }

    public User update(UserMutationDto dto) {
        var current = userRepo.findById(dto.getId());
        if (current.isPresent()) {
            var updatedUser = UserMapper.convert(current.get(), dto);
            return userRepo.save(updatedUser);
        }
        return null;
    }

    public void delete(Integer id) {
        userRepo.deleteById(id);
    }
}