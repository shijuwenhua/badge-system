package com.shijuwenhua.signin.model;

public class BadgeDto {
	private long id;
	private String title;
	private String description;
	private String icon;
	private int upgradeRequiredTime;
	private Long upgradeBadgeId;
	private String upgradeBadgeTitle;
	private int completedRequiredActivities;
	private String achievementTime;
	private String status;
	
	
	public BadgeDto(Badge badge, String achievementTime, String status) {
		this.id = badge.getId();
		this.title = badge.getTitle();
		this.description = badge.getDescription();
		this.icon = badge.getIcon();
		this.upgradeRequiredTime = badge.getUpgradeRequiredTimes();
		this.upgradeBadgeId = badge.getUpgradeBadgeId();
		this.upgradeBadgeTitle = badge.getUpgradeBadgeTitle();
		this.achievementTime = achievementTime;
		this.status = status;
		this.completedRequiredActivities = badge.getCompletedRequiredActivities();
	}
	
	public BadgeDto(Badge badge) {
		this.id = badge.getId();
		this.title = badge.getTitle();
		this.description = badge.getDescription();
		this.icon = badge.getIcon();
		this.upgradeRequiredTime = badge.getUpgradeRequiredTimes();
		this.upgradeBadgeId = badge.getUpgradeBadgeId();
		this.upgradeBadgeTitle = badge.getUpgradeBadgeTitle();
		this.completedRequiredActivities = badge.getCompletedRequiredActivities();
	}

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

	public int getCompletedRequiredActivities() {
		return completedRequiredActivities;
	}

	public void setCompletedRequiredActivities(int completedRequiredActivities) {
		this.completedRequiredActivities = completedRequiredActivities;
	}

	public String getUpgradeBadgeTitle() {
		return upgradeBadgeTitle;
	}

	public void setUpgradeBadgeTitle(String upgradeBadgeTitle) {
		this.upgradeBadgeTitle = upgradeBadgeTitle;
	}

	public String getAchievementTime() {
		return achievementTime;
	}

	public void setAchievementTime(String achievementTime) {
		this.achievementTime = achievementTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
