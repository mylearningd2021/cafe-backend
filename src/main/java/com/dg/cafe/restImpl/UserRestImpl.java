package com.dg.cafe.restImpl;

import com.dg.cafe.constants.CafeConstants;
import com.dg.cafe.rest.UserRest;
import com.dg.cafe.service.UserService;
import com.dg.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {


    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return service.signUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
