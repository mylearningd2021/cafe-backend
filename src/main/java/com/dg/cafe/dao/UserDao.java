package com.dg.cafe.dao;

import com.dg.cafe.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    //we are provding its impl in User class NamedQuery
    User findByEmail(@Param("email") String email);
}
