package com.dg.cafe.restImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.dao.UserDao;
import com.dg.cafe.rest.UserRest;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Slf4j
@RestController
@CrossOrigin
public class UserRestImpl implements UserRest {


    @Autowired
    private UserService service;

    @Autowired
    private UserDao repo;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
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
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            ResponseEntity<String> loginMesssage = service.login(requestMap);
            return loginMesssage;
        }catch (Exception e)
        {
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> signUp() {
        return new ResponseEntity<>("working fine...",HttpStatus.OK);
    }
}
