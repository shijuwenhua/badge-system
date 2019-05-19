package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;

public interface ActivityBadgeService {

    public List<ActivityBadge> getActivityBadgeList();
    
    public List<ActivityBadge> findActivityBadgesByActivityId(long activityId);

    public Badge findBadgesByActivityId(long activityId);
    
    public List<Activity> findActivitiesByBadgeId(long badgeId);
    
    public ActivityBadge findActivityBadgeByActivityIdAndBadgeId(long activityId, long badgeId);

    public void save(ActivityBadge activityBadge);

    public void edit(ActivityBadge activityBadge);

    public void delete(long activityBadgeId);

    public ActivityBadge findActivityBadgeById(long activityBadgeId);
    
	void deleteByActivityIdAndBadgeId(long activityId, long badgeId);

	List<ActivityBadge> findActivityBadgesByBadgeId(long badgeId);
	
	List<ActivityBadge> findActivityBadgesByBadgeIdAndUserOpenId(long badgeId, String userOpenId);
}
