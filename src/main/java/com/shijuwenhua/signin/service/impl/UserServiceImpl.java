package com.shijuwenhua.signin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.repository.UserBadgeRepository;
import com.shijuwenhua.signin.repository.UserRepository;
import com.shijuwenhua.signin.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public User findUserByOpenId(String openId) {
    	return userRepository.findByOpenId(openId);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

	@Override
	public List<Activity> findActivitiesByUserId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BadgeDto> findBadgesByUserId(String userOpenId) {
		// TODO Auto-generated method stub
		return userBadgeRepository.findBadgesByUserId(userOpenId);
	}
	
	@Override
	public BadgeDto findBadgesByUserId(long badgeId, String userOpenId) {
		// TODO Auto-generated method stub
		return userBadgeRepository.findBadgesByUserId(badgeId, userOpenId);
	}
}


