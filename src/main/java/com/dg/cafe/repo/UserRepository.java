package com.dg.cafe.repo;
import com.dg.cafe.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);

    void updateStatus(String status, String id);

}
