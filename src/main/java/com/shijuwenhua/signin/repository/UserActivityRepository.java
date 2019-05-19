package com.shijuwenhua.signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

	@Query("select t from UserActivity t where t.userOpenId = ?1 and t.activityId = ?2")
	UserActivity findByUserIdAndActivityId(String userOpenId, long activityId);
    
	UserActivity findByActivityId(long activityId);

    void deleteByUserOpenId(String userOpenId);
    
    void deleteByActivityId(long activityId);
}