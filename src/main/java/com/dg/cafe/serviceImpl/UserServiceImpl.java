package com.dg.cafe.serviceImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.jwt.LoginService;
import com.dg.cafe.repo.UserRepository;
import com.dg.cafe.dto.UserDto;
import com.dg.cafe.jwt.CustomeUserDetailsService;
import com.dg.cafe.jwt.JwtFilter;
import com.dg.cafe.jwt.JwtUtils;
import com.dg.cafe.model.User;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import com.dg.cafe.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
       try {
           if (UserServiceImpl.validateSignUpMap(requestMap)) {
               User user = repository.findByEmail(requestMap.get("email"));
               if (Objects.isNull(user)) {
                   repository.save(getUserFromMap(requestMap));
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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        return loginService.authenticateCafeUser(requestMap);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserDto> dtoUsers = new ArrayList<>();
        List<User> users = null;
        try {
            users = repository.findAll();
            //modelMapper.map(users,dtoUsers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public ResponseEntity<String> update(String status, String id) {
        if(jwtFilter.isAdmin())
        {
            Optional<User> userOptional = repository.findById(Integer. valueOf(id));
            if(userOptional.isPresent())
            {
                repository.updateStatus(status, Integer. valueOf(id));
                sendMailToAllAdmin(status,userOptional.get().getEmail(), repository.getAllAdmin());
                return new ResponseEntity<>("User status updated sucessfully...", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.OK);
        }
    }

    private void sendMailToAllAdmin(String status, String email, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true"))
        {
            emailUtils.sendSimpleMail(jwtFilter.getCurrentUser(),"Account Approved","Account Enabled for user -"+email+"by admin"+jwtFilter.getCurrentUser(), allAdmin);
        }else {
            log.error("UserServiceImpl: sendMailToAllAdmin() : else block");
        }

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