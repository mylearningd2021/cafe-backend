package com.dg.cafe.jwt;

import com.dg.cafe.dao.UserDao;
import com.dg.cafe.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    User cafeUser = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername : username ->"+username);
        cafeUser = userDao.findByEmail(username);
        if (!Objects.isNull(cafeUser)) {
            return new org.springframework.security.core.userdetails.User(cafeUser.getEmail(),
                    cafeUser.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public User getCafeUser(){
        return cafeUser;
    }
}
