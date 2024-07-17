package com.dg.cafe.serviceImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.dao.UserDao;
import com.dg.cafe.dto.UserDto;
import com.dg.cafe.jwt.CustomeUserDetailsService;
import com.dg.cafe.jwt.JwtUtils;
import com.dg.cafe.pojo.User;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;
    @Autowired
    private ModelMapper modelMapper;
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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if(authentication.isAuthenticated())
            {
                if(customeUserDetailsService.getCafeUser().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<>("\"token\": \""+
                            jwtUtils.generateToken(customeUserDetailsService.getCafeUser().getEmail(),
                                    customeUserDetailsService.getCafeUser().getRole())+"\"}",
                            HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("\"message\": \""+"Wait for admin approval"+"\"}"
                            ,HttpStatus.OK);
                }

            }
        }catch (Exception e)
        {
            log.error("UserRestControllerImpl: login() - Inside Catch block -"+e);
        }
        return new ResponseEntity<>("Bad Credentials"
                ,HttpStatus.OK);
    }

    @Override
    public List<User> getAllUsers() {
        //UserDto userDto = new UserDto();
        List<UserDto> dtoUsers = new ArrayList<>();
        List<User> users = null;
        try {
            users = userDao.findAll();
            //modelMapper.map(users,dtoUsers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
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