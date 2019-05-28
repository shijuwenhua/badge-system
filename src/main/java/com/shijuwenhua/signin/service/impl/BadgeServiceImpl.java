package com.shijuwenhua.signin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.repository.BadgeRepository;
import com.shijuwenhua.signin.service.BadgeService;

@Service
public class BadgeServiceImpl implements BadgeService{

    @Autowired
    private BadgeRepository badgeRepository;

	@Override
	public List<Badge> getAllBadges() {
		// TODO Auto-generated method stub
		return badgeRepository.findAll();
	}
	
	@Override
	public List<Badge> getHighBadges() {
		// TODO Auto-generated method stub
		return badgeRepository.getHighLevelBadges();
	}

	@Override
	public List<User> findUserByBadgeId(long badgeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Badge findBadgeById(long badgeId) {
		// TODO Auto-generated method stub
		return badgeRepository.findById(badgeId);
	}

	@Override
	public void save(Badge badge) {
		// TODO Auto-generated method stub
		badgeRepository.save(badge);
	}

	@Override
	public void edit(Badge badge) {
		// TODO Auto-generated method stub
		badgeRepository.save(badge);
	}

	@Override
	public void delete(long badgeId) {
		// TODO Auto-generated method stub
		badgeRepository.deleteById(badgeId);
	}

	@Override
	public List<Badge> getEditBadgeList(long badgeId, long upgradeBadgeId) {
		// TODO Auto-generated method stub
		return badgeRepository.getEditBadgesList(badgeId, upgradeBadgeId);
	}
	
	@Override
	public List<Badge> getActivityEditBadgesList(long badgeId) {
		// TODO Auto-generated method stub
		return badgeRepository.getActivityEditBadgesList(badgeId);
	}

	@Override
	public List<BadgeDto> getSubBadgesList(long id) {
		// TODO Auto-generated method stub
		return badgeRepository.getSubBadgesList(id);
	}
	
	@Override
	public List<Badge> getSubBadges(long id) {
		// TODO Auto-generated method stub
		return badgeRepository.getSubBadges(id);
	}
	
	@Override
	public List<BadgeDto> getUserSubBadgesList(long id, String userOpenId) {
		// TODO Auto-generated method stub
		return badgeRepository.getUserBadgesList(id, userOpenId);
	}

	@Override
	public Badge getUpgradeBadge(long id) {
		// TODO Auto-generated method stub
		return badgeRepository.getUpgradeBadge(id);
	}
	
	@Override
	public Badge findBadgesByActivityId(long activityId) {
		// TODO Auto-generated method stub
		return badgeRepository.findBadgesByActivityId(activityId);
	}
	
	@Override
	public void updateBadgeCoreActivities(long badgeId) {
		// TODO Auto-generated method stub
		badgeRepository.updateBadgeCoreActivities(badgeId);
	}
}


