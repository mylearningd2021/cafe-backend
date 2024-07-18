package com.dg.cafe.jwt;

import com.dg.cafe.constants.CafeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<String> authenticateCafeUser(Map<String, String> requestMap) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
        );
        ResponseEntity<String> response = null;
        if (authentication.isAuthenticated()) {
            if (customeUserDetailsService.getCafeUser().getStatus().equalsIgnoreCase("true")) {
                response = new  ResponseEntity<>("\"token\": \"" +
                        jwtUtils.generateToken(customeUserDetailsService.getCafeUser().getEmail(),
                                customeUserDetailsService.getCafeUser().getRole()) + "\"}",
                        HttpStatus.OK);

            }
        } else {
            response = new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
