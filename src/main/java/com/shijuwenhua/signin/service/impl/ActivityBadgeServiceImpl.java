package com.shijuwenhua.signin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.repository.ActivityBadgeRepository;
import com.shijuwenhua.signin.service.ActivityBadgeService;

@Service
public class ActivityBadgeServiceImpl implements ActivityBadgeService{
    
    @Autowired
    private ActivityBadgeRepository activityBadgeRepository;

	@Override
	public ActivityBadge findActivityBadgeById(long activityBadgeId) {
		// TODO Auto-generated method stub
		return activityBadgeRepository.findById(activityBadgeId);
	}

	@Override
	public List<ActivityBadge> getActivityBadgeList() {
		// TODO Auto-generated method stub
		return activityBadgeRepository.findAll();
	}

	@Override
	public ActivityBadge findActivityBadgeByActivityIdAndBadgeId(long activityId, long badgeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ActivityBadge activityBadge) {
		// TODO Auto-generated method stub
		activityBadgeRepository.save(activityBadge);
	}

	@Override
	public void edit(ActivityBadge activityBadge) {
		// TODO Auto-generated method stub
		activityBadgeRepository.save(activityBadge);
	}

	@Override
	public void deleteByActivityIdAndBadgeId(long activityId, long badgeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long activityBadgeId) {
		// TODO Auto-generated method stub
		activityBadgeRepository.deleteById(activityBadgeId);
	}

	@Override
	public ActivityBadge findActivityBadgesByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return activityBadgeRepository.findActivityBadgesByActivityId(activityId);
	}
	
	@Override
	public List<ActivityBadge> findActivityBadgesByBadgeId(long badgeId) {
		// TODO Auto-generated method stub
		return activityBadgeRepository.findActivityBadgesByBadgeId(badgeId);
	}

	@Override
	public List<ActivityBadge> findUserCompletedActivity(long badgeId, String userOpenId) {
		// TODO Auto-generated method stub
		return activityBadgeRepository.findUserCompletedActivity(badgeId, userOpenId);
	}

}


