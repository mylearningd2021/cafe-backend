package com.dg.cafe.serviceImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.dao.UserDao;
import com.dg.cafe.pojo.User;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
       try {
           if (UserServiceImpl.validateSignUpMap(requestMap)) {
               User user = userDao.findByEmail(requestMap.get("email"));
               if (Objects.isNull(user)) {
                   userDao.save(getUserFromMap(requestMap));
                   return CafeUtils.getResponseEntity("User registered successfully", HttpStatus.OK);
               } else {
                   return CafeUtils.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
               }
           } else {
               return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
           }
       }catch (Exception e)
       {
            e.printStackTrace();
       }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setRole("user");
        user.setStatus("false");
        return user;


    }


    public static boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        } else {
            return false;
        }
    }
}