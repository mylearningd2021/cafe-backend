package com.dg.cafe.repo;

import com.dg.cafe.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class UserRepositoryImpl implements UserRepository{

    @Autowired
    private UserRepository repository;
    @Override
    public void updateStatus(String status, String id) {

        Optional<User> user = repository.findById(Integer.parseInt(id));
        if (user.isEmpty()) {

        } else {

        }
    }
}
