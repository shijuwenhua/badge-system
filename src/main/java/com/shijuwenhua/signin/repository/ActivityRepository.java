package com.shijuwenhua.signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shijuwenhua.signin.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Activity findById(long id);

    void deleteById(Long id);
    
}