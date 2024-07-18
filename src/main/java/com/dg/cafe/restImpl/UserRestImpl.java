package com.dg.cafe.restImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.repo.UserRepository;
import com.dg.cafe.jwt.JwtFilter;
import com.dg.cafe.model.User;
import com.dg.cafe.rest.UserRest;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class UserRestImpl implements UserRest {

    @Autowired
    private UserService service;

    //@Autowired
   // private UserRepository repo;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUpApi(Map<String, String> requestMap) {
        try {
            log.info("Inside signUp(): try block ");
            return service.signUp(requestMap);
        } catch (Exception e) {
            log.info("Inside signUp(): catch block ");
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> loginApi(Map<String, String> requestMap) {
        log.info("Inside login() : UserRestImpl class ");
        try {
            ResponseEntity<String> loginMesssage = service.login(requestMap);
            return loginMesssage;
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> test() {
        if (jwtFilter.isAdmin()) {
            return new ResponseEntity<>("Working fine with Admin role", HttpStatus.OK);
        }else if(jwtFilter.isUser()){
             return new ResponseEntity<>("Working fine with User role", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not Authorized", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsersApi() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<String> updateApi(Map<String, String> requestMap) {
        try {
            log.info("Inside updateApi() try block : UserRestImpl");
            return service.update(requestMap.get("status"), requestMap.get("id"));
        } catch (Exception e) {
            log.info("Inside updateApi() catch block : UserRestImpl");
            return new ResponseEntity<>("Updation Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
