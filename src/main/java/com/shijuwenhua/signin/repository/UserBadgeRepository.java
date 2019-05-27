package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

//	UserBadge findByUserId(String userOpenId);
    
	@Query("select ub from UserBadge ub where ub.badgeId = ?1 and ub.userOpenId = ?2")
	UserBadge findByBadgeId(long badgeId, String userOpenId);

//    void deleteByUserId(String userOpenId);
    
    void deleteByBadgeId(long badgeId);
    
    @Query("select t from Badge t, UserBadge ub where t.id = ub.badgeId and ub.userOpenId = ?1")
	List<Badge> findBadgesByUserId(String userOpenId);
    
//    @Query("select new com.shijuwenhua.signin.model.UserBadgeInfo(ub, b) from UserBadge ub, Badge b where b.id = ?1 and ub.badgeId = b.upgradeBadgeId")
//	UserBadgeInfo findUserUpgradeBadge(long badgeId, String userOpenId);
}