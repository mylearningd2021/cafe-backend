package com.dg.cafe.rest;

import com.dg.cafe.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path =  "/signup")
    public ResponseEntity<String> signUpApi(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path =  "/login")
    public ResponseEntity<String> loginApi(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path =  "/test")
    public ResponseEntity<String> test();

    @GetMapping(path =  "/users")
    public ResponseEntity<List<User>> getAllUsersApi();

    @PutMapping(path =  "/update")
    public ResponseEntity<String> updateApi(@RequestBody(required = true) Map<String, String> requestMap);


}
