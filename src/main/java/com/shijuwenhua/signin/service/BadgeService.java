package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.User;

public interface BadgeService {

    public List<Badge> getAllBadges();
    
    public List<Badge> getEditBadgeList(long badgeId);

    public List<User> findUserByBadgeId(long badgeId);
    
    public Badge findBadgeById(long badgeId);

    public void save(Badge badge);

    public void edit(Badge badge);

    public void delete(long badgeId);

    List<BadgeDto> getSubBadgesList(long id);
    
    Badge getUpgradeBadge(long id);

	Badge findBadgesByActivityId(long activityId);

	List<BadgeDto> getUserSubBadgesList(long id, String userOpenId);

	List<Badge> getHighBadges();

	List<Badge> getSubBadges(long id);

	List<Badge> getActivityEditBadgesList();

//	int updateBadgeCoreActivities(long badgeId);
}
