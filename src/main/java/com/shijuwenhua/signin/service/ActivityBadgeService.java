package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.ActivityBadge;

public interface ActivityBadgeService {

    public List<ActivityBadge> getActivityBadgeList();
    
    public List<ActivityBadge> findActivityBadgesByActivityId(long activityId);

    public ActivityBadge findActivityBadgeByActivityIdAndBadgeId(long activityId, long badgeId);

    public void save(ActivityBadge activityBadge);

    public void edit(ActivityBadge activityBadge);

    public void delete(long activityBadgeId);

    public ActivityBadge findActivityBadgeById(long activityBadgeId);
    
	void deleteByActivityIdAndBadgeId(long activityId, long badgeId);

	List<ActivityBadge> findActivityBadgesByBadgeId(long badgeId);
	
	List<ActivityBadge> findUserCompletedActivity(long badgeId, String userOpenId);
}
