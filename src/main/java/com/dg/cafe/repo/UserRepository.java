package com.dg.cafe.repo;
import com.dg.cafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("update User set status=:status where id=:id")
    void updateStatus(String status, Integer id);

    @Query("from User")
     List<String> getAllAdmin();

}
