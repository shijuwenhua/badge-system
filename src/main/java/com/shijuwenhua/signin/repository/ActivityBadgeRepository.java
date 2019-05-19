package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.model.Badge;

public interface ActivityBadgeRepository extends JpaRepository<ActivityBadge, Long> {

	@Query("select t from Badge t, ActivityBadge ab where t.id = ab.badgeId and ab.activityId = ?1")
	Badge findBadgesByActivityId(long activityId);

	@Query("select t from Activity t, ActivityBadge ab where t.id = ab.activityId and ab.badgeId = ?1")
	List<Activity> findActivitiesByBadgeId(long badgeId);

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
	List<ActivityBadge> findActivityBadgesByBadgeIdAndUserOpenId(long badgeId, String userOpenId);
}