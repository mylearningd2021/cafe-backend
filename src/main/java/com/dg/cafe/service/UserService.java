package com.dg.cafe.service;

import com.dg.cafe.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    List<User> getAllUsers();

    ResponseEntity<String> update(String status, String id);


}
