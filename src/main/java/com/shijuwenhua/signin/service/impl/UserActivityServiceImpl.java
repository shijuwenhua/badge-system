package com.shijuwenhua.signin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityDto;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.model.UserActivity;
import com.shijuwenhua.signin.repository.ActivityRepository;
import com.shijuwenhua.signin.repository.UserActivityRepository;
import com.shijuwenhua.signin.service.UserActivityService;

@Service
public class UserActivityServiceImpl implements UserActivityService{
    
    @Autowired
    private UserActivityRepository userActivityRepository;
    
    @Autowired
    private ActivityRepository activityRepository;

	@Override
	public List<UserActivity> getUserActivityList() {
		// TODO Auto-generated method stub
		return userActivityRepository.findAll();
	}

	@Override
	public List<User> findUsersByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> findActivitiesByUserId(long userOpenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(UserActivity userActivity) {
		// TODO Auto-generated method stub
		userActivityRepository.save(userActivity);
	}

	@Override
	public void edit(UserActivity userActivity) {
		// TODO Auto-generated method stub
		userActivityRepository.save(userActivity);
	}

	@Override
	public void delete(long activityUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByActivityIdAndUserId(long activityId, long userOpenId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserActivity> findByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserActivity> findByUserId(String userOpenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserActivity findByUserIdAndActivityId(long activityId, String userOpenId) {
		// TODO Auto-generated method stub
		return userActivityRepository.findByUserIdAndActivityId(userOpenId, activityId);
	}

	@Override
	public List<ActivityDto> findActivitiesByBadgeId(long badgeId, String userOpenId, String queryStatus) {
		// TODO Auto-generated method stub
		return activityRepository.findUADtoByBadgeIdAndUserOpenId(badgeId, userOpenId, queryStatus);
	}
}


