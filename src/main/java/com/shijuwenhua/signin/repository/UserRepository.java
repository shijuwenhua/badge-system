package com.shijuwenhua.signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    void deleteById(Long id);
    
    @Query("select t from User t where t.openId = ?1")
    User findByOpenId(String openId);
    
    
}