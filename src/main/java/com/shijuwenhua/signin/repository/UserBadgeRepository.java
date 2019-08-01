package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

//	UserBadge findByUserId(String userOpenId);
    
	@Query("select ub from UserBadge ub where ub.badgeId = ?1 and ub.userOpenId = ?2")
	UserBadge findByBadgeIdAndUserId(long badgeId, String userOpenId);

//    void deleteByUserId(String userOpenId);
    
    void deleteByBadgeId(long badgeId);
    
    @Query("select new com.shijuwenhua.signin.model.BadgeDto(b, ub.achievementTime, ub.status)  from Badge b, UserBadge ub where b.id = ub.badgeId and ub.userOpenId = ?1")
	List<BadgeDto> findBadgesByUserId(String userOpenId);
    
    @Query("select new com.shijuwenhua.signin.model.BadgeDto(b, ub.achievementTime, ub.status)  from Badge b, UserBadge ub where b.id = ub.badgeId and ub.userOpenId = ?2 and b.id = ?1")
    BadgeDto findBadgesByUserId(long badgeId, String userOpenId);
    
    @Transactional
	@Modifying
    @Query(value = "update user_badge set status = ?2, achievement_time = ?3 where badge_id = ?1", nativeQuery = true)
    int updateCommonUserBadges(long badgeId, String status, String achievementTime);
    
//    @Query("select new com.shijuwenhua.signin.model.UserBadgeInfo(ub, b) from UserBadge ub, Badge b where b.id = ?1 and ub.badgeId = b.upgradeBadgeId")
//	UserBadgeInfo findUserUpgradeBadge(long badgeId, String userOpenId);
}