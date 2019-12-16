package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityDto;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Activity findById(long id);

	void deleteById(Long id);

	@Query("select t from Activity t, ActivityBadge ab where t.id = ab.activityId and ab.badgeId = ?1")
	List<Activity> findActivitiesByBadgeId(long badgeId);
	
	@Query("select new com.shijuwenhua.signin.model.ActivityDto(a) from Activity a, ActivityBadge ab "
			+ "where a.id = ab.activityId and ab.badgeId = ?1")
	List<ActivityDto> findActivitiesDtoByBadgeId(long badgeId);
	
	@Query("select new com.shijuwenhua.signin.model.ActivityDto(a, ua.status, ua.attendTimes, ua.comments, ua.commonTotalAttend) "
			+ "from UserActivity ua, Activity a where a.id = ?1 and ua.userOpenId = ?2 and a.id = ua.activityId")
	ActivityDto findUserActivityDto(long activityId, String userOpenId);

	@Query(value = "select new com.shijuwenhua.signin.model.ActivityDto(a, ua.status, ua.attendTimes, ua.comments, ua.commonTotalAttend) "
			+ "from ActivityBadge ab, UserActivity ua, Activity a where ab.activityId = ua.activityId "
			+ "and ab.activityId = a.id and ab.badgeId = ?1 and ua.userOpenId = ?2 and ua.status != ?3")
	List<ActivityDto> findUADtoByBadgeIdAndUserOpenId(long badgeId, String userOpenId, String status);

}