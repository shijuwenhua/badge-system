package com.shijuwenhua.signin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.repository.BadgeRepository;
import com.shijuwenhua.signin.service.BadgeService;

@Service
public class BadgeServiceImpl implements BadgeService{

    @Autowired
    private BadgeRepository badgeRepository;

	@Override
	public List<Badge> getBadgeList() {
		// TODO Auto-generated method stub
		return badgeRepository.findAll();
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
	public List<Badge> getEditBadgeList(long badgeId) {
		// TODO Auto-generated method stub
		return badgeRepository.getEditBadgesList(badgeId);
	}

	@Override
	public List<Badge> getSubBadgesList(long id) {
		// TODO Auto-generated method stub
		return badgeRepository.getSubBadgesList(id);
	}

	@Override
	public Badge getUpgradeBadge(long id) {
		// TODO Auto-generated method stub
		return badgeRepository.getUpgradeBadge(id);
	}

}

