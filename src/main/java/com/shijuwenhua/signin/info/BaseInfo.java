package com.shijuwenhua.signin.info;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.service.BadgeService;

@Service
public class BaseInfo {

	@Autowired
	private BadgeService badgeService;

	private static ConcurrentHashMap<Long, List<Badge>> allSubBadges;

	private static ConcurrentHashMap<Long, Badge> allUpGradeBadges;

	private static ConcurrentHashMap<Long, Badge> badgeByActivityId;

	private static ConcurrentHashMap<Long, List<Activity>> allSubActivities;

	private List<Badge> badges;

	public ConcurrentHashMap<Long, List<Badge>> getAllSubBadges() {
		if (allSubBadges == null || allSubBadges.isEmpty())
			setAllSubBadges();
		return allSubBadges;
	}

	public void setAllSubBadges() {
		allSubBadges = new ConcurrentHashMap<Long, List<Badge>>();
		badges = badges == null || badges.isEmpty() ? badgeService.getAllBadges() : badges;
		for (Badge badge : badges) {
			allSubBadges.put(badge.getId(), badgeService.getSubBadges(badge.getId()));
		}
	}

	public ConcurrentHashMap<Long, Badge> getAllUpGradeBadges() {
		return allUpGradeBadges;
	}

	public void setAllUpGradeBadges() {
		badges = badges == null || badges.isEmpty() ? badgeService.getAllBadges() : badges;
		allUpGradeBadges = new ConcurrentHashMap<Long, Badge>();
		for (Badge badge : badges) {
			Badge upgradeBadge = badgeService.findBadgeById(badge.getId());
			allUpGradeBadges.put(badge.getId(), upgradeBadge == null?new Badge():upgradeBadge);
		}
	}

	public ConcurrentHashMap<Long, Badge> getBadgeByActivityId() {
		return badgeByActivityId;
	}

	public void setBadgeByActivityId(ConcurrentHashMap<Long, Badge> badgeByActivityId) {
		this.badgeByActivityId = badgeByActivityId;
	}

	public ConcurrentHashMap<Long, List<Activity>> getAllSubActivities() {
		return allSubActivities;
	}

	public void setAllSubActivities(ConcurrentHashMap<Long, List<Activity>> allSubActivities) {
		this.allSubActivities = allSubActivities;
	}

//	private 
}
