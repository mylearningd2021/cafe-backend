package com.dg.cafe.dao;
import com.dg.cafe.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);

}
