package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.ActivityBadge;

public interface ActivityBadgeRepository extends JpaRepository<ActivityBadge, Long> {

	@Query("delete from ActivityBadge t where t.activityId = ?1 and t.badgeId = ?2")
	void delete(long activityId, long badgeId);

	@Query("select t from ActivityBadge t where t.activityId = ?1 and t.badgeId = ?2")
	ActivityBadge findActivityBadgeByActivityIdAndBadgeId(long activityId, long badgeId);

	ActivityBadge findById(long id);

	void deleteById(Long id);

	@Query("select t from ActivityBadge t where t.activityId = ?1")
	List<ActivityBadge> findActivityBadgesByActivityId(long activityId);

	@Query("select t from ActivityBadge t where t.badgeId = ?1")
	List<ActivityBadge> findActivityBadgesByBadgeId(long badgeId);

	@Query(value = "select ab.* from activity_badge ab, user_activity ua where ab.badge_id = ?1 and ab.activity_id = ua.activity_id and ua.user_open_id = ?2 and ua.status = 'Completed'", nativeQuery = true)
	List<ActivityBadge> findUserCompletedActivity(long badgeId, String userOpenId);
}