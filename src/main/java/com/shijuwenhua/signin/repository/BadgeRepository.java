package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDto;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

	Badge findById(long id);

	void deleteById(Long id);
	
	@Query("select t from Badge t where t.completedRequiredActivities = 0 and t.id != ?1 and t.upgradeBadgeId != ?1")
	List<Badge> getEditBadgesList(long id);

	@Query("select new com.shijuwenhua.signin.model.BadgeDto(b) from Badge b where b.upgradeBadgeId = ?1")
	List<BadgeDto> getSubBadgesList(long id);
	
	@Query("select b from Badge b where b.upgradeBadgeId = ?1")
	List<Badge> getSubBadges(long id);
	
	@Query(value = "select new com.shijuwenhua.signin.model.BadgeDto(b, ub.achievementTime, ub.status) "
			+ "from Badge b, UserBadge ub where ub.badgeId = b.id "
			+ "and b.upgradeBadgeId = ?1 and ub.userOpenId = ?2")
	List<BadgeDto> getUserBadgesList(long id,String userOpenId);

	@Query(value = "select b1.* from badge b1, badge b2 "
			+ "where b1.id = b2.upgrade_badge_id and b2.id = ?1", 
			nativeQuery = true)
	Badge getUpgradeBadge(long id);
	
	@Query("select t from Badge t, ActivityBadge ab where t.id = ab.badgeId "
			+ "and ab.activityId = ?1")
	Badge findBadgesByActivityId(long activityId);
	
	@Query("select b from Badge b where b.completedRequiredActivities = 0")
	List<Badge> getHighLevelBadges();
}