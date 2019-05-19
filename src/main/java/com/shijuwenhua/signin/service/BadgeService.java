package com.shijuwenhua.signin.service;

import java.util.List;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;

public interface BadgeService {

    public List<Badge> getBadgeList();
    
    public List<Badge> getEditBadgeList(long badgeId);

    public List<User> findUserByBadgeId(long badgeId);
    
    public Badge findBadgeById(long badgeId);

    public void save(Badge badge);

    public void edit(Badge badge);

    public void delete(long badgeId);

    List<Badge> getSubBadgesList(long id);
    
    Badge getUpgradeBadge(long id);
}
