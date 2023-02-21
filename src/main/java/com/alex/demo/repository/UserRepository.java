package com.alex.demo.repository;

import com.alex.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findUserById(Long id);


    @Query(value = "select * from users " +
            "join user_roles on users.id = user_roles.user_id " +
            "where role_id = 2",
            nativeQuery = true)
    List<User> findAllUsers();

}
