package com.shijuwenhua.signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shijuwenhua.signin.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

	@Query("select t from UserActivity t where t.userOpenId = ?1 and t.activityId = ?2")
	UserActivity findByUserIdAndActivityId(String userOpenId, long activityId);
    
	UserActivity findByActivityId(long activityId);

    void deleteByUserOpenId(String userOpenId);
    
    void deleteByActivityId(long activityId);
    
    @Transactional
    @Modifying
    @Query(value = "update user_activity set attend_times = ?2, status = ?4, version = version + 1, achievement_time = ?5  where id = ?1 and version = ?3", nativeQuery = true)
    int updateCommonActivity(Long id, int attendTimes, Long version, String status, String achievementTime);
    
    @Transactional
    @Modifying
    @Query(value = "update user_activity set status = ?2,  achievement_time = ?3  where activity_id = ?1", nativeQuery = true)
    int updateUserCommonActivities(Long activityId, String status, String achievementTime);
}