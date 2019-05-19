package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

//	UserBadge findByUserId(String userOpenId);
    
	@Query("select t from UserBadge t where t.badgeId = ?1")
	UserBadge findByBadgeId(long badgeId);

//    void deleteByUserId(String userOpenId);
    
    void deleteByBadgeId(long badgeId);
    
    @Query("select t from Badge t, UserBadge ub where t.id = ub.badgeId and ub.userOpenId = ?1")
	List<Badge> findBadgesByUserId(String userOpenId);
}