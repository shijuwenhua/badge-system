package com.shijuwenhua.signin.model;

import java.util.List;

public class BadgeDto {
	
	private long id;
	private String title;
	private String description;
	private String icon;
	private int upgradeRequiredTime;
	private Long upgradeBadgeId;
	private String upgradeBadgeTitle;
	private List<Activity> activityList;
	private List<Badge> badgeList;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUpgradeRequiredTime() {
		return upgradeRequiredTime;
	}

	public void setUpgradeRequiredTime(int upgradeRequiredTime) {
		this.upgradeRequiredTime = upgradeRequiredTime;
	}

	public Long getUpgradeBadgeId() {
		return upgradeBadgeId;
	}

	public void setUpgradeBadgeId(Long upgradeBadgeId) {
		this.upgradeBadgeId = upgradeBadgeId;
	}

	public String getUpgradeBadgeTitle() {
		return upgradeBadgeTitle;
	}

	public void setUpgradeBadgeTitle(String upgradeBadgeTitle) {
		this.upgradeBadgeTitle = upgradeBadgeTitle;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public List<Badge> getBadgeList() {
		return badgeList;
	}

	public void setBadgeList(List<Badge> badgeList) {
		this.badgeList = badgeList;
	}

}
