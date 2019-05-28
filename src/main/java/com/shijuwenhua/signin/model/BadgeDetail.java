package com.shijuwenhua.signin.model;

import java.util.List;

public class BadgeDetail {
	
	private long id;
	private String title;
	private String description;
	private String icon;
	private int upgradeRequiredTime;
	private Long upgradeBadgeId;
	private String upgradeBadgeTitle;
	private int completedRequiredActivities;
	private String status;
	private String achievementTime;
	private List<ActivityDto> userActivityList;
	private List<BadgeDto> badgeList;

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

	public int getCompletedRequiredActivities() {
		return completedRequiredActivities;
	}

	public void setCompletedRequiredActivities(int completedRequiredActivities) {
		this.completedRequiredActivities = completedRequiredActivities;
	}

	public void setUpgradeBadgeTitle(String upgradeBadgeTitle) {
		this.upgradeBadgeTitle = upgradeBadgeTitle;
	}

	public List<ActivityDto> getUserActivityList() {
		return userActivityList;
	}

	public void setUserActivityList(List<ActivityDto> userActivityList) {
		this.userActivityList = userActivityList;
	}

	public List<BadgeDto> getBadgeList() {
		return badgeList;
	}

	public void setBadgeList(List<BadgeDto> badgeList) {
		this.badgeList = badgeList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAchievementTime() {
		return achievementTime;
	}

	public void setAchievementTime(String achievementTime) {
		this.achievementTime = achievementTime;
	}
}
