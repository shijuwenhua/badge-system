package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.User;

public interface UserService {

    public List<User> getUserList();

    public List<Activity> findActivitiesByUserId(long userId);
    
    public User findUserById(long id);

    public void save(User user);

    public void edit(User user);

    public void delete(long userId);

	public User findUserByOpenId(String openId);

	List<BadgeDto> findBadgesByUserId(String userOpenId);

	BadgeDto findBadgesByUserId(long badgeId, String userOpenId);

}
