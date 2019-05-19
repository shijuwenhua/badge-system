package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.model.UserActivity;

public interface UserActivityService {

    public List<UserActivity> getUserActivityList();
    
    public List<UserActivity> findByActivityId(long activityId);
    
    public List<UserActivity> findByUserId(String userOpenId);
    
    public UserActivity findByUserIdAndActivityId(long activityId, String userOpenId);

    public List<User> findUsersByActivityId(long activityId);
    
    public List<Activity> findActivitiesByUserId(long userOpenId);
    
    public void save(UserActivity activityUser);

    public void edit(UserActivity activityUser);

    public void delete(long activityUserId);

	void deleteByActivityIdAndUserId(long activityId, long userOpenId);
}
