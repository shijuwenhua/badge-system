package com.shijuwenhua.signin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.model.ActivityDto;
import com.shijuwenhua.signin.repository.ActivityRepository;
import com.shijuwenhua.signin.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private ActivityRepository activityRepository;
    
	@Override
	public List<Activity> getActivityList() {
		return activityRepository.findAll();
	}

	@Override
	public List<Badge> findBadgeByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUserByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity findActivityById(long activityId) {
		// TODO Auto-generated method stub
		return activityRepository.findById(activityId);
	}

	@Override
	public void save(Activity activity) {
		// TODO Auto-generated method stub
		activityRepository.save(activity);
	}

	@Override
	public void edit(Activity activity) {
		// TODO Auto-generated method stub
		activityRepository.save(activity);
	}

	@Override
	public void delete(long activityId) {
		// TODO Auto-generated method stub
		activityRepository.deleteById(activityId);
	}
	
	@Override
	public String checkActivityStatus(long activityId, int attendanceTimes) {
		Activity activity = findActivityById(activityId);
		if(attendanceTimes >= activity.getRequiredAttendTimes()) {
			return "Compeleted";
		}
		return "Processing";
	}
	
	@Override
	public List<Activity> findActivitiesByBadgeId(long badgeId) {
		// TODO Auto-generated method stub
		return activityRepository.findActivitiesByBadgeId(badgeId);
	}
	
	@Override
	public List<ActivityDto> findActivitiesDtoByBadgeId(long badgeId) {
		// TODO Auto-generated method stub
		return activityRepository.findActivitiesDtoByBadgeId(badgeId);
	}

}


