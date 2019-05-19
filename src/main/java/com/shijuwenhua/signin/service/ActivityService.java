package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;

public interface ActivityService {

    public List<Activity> getActivityList();

    public List<Badge> findBadgeByActivityId(long activityId);
    
    public List<User> findUserByActivityId(long activityId);
    
    public Activity findActivityById(long activityId);

    public void save(Activity activity);

    public void edit(Activity activity);

    public void delete(long activityId);

	String checkActivityStatus(long activityId, int attendanceTimes);

}
