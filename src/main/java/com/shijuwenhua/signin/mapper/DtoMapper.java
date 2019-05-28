package com.shijuwenhua.signin.mapper;

import com.shijuwenhua.signin.model.BadgeDetail;
import com.shijuwenhua.signin.model.BadgeDto;

public class DtoMapper {

	public BadgeDetail badgeMapper(BadgeDto badgeDto) {
		BadgeDetail badgeDetail = new BadgeDetail();
		badgeDetail.setId(badgeDto.getId());
		badgeDetail.setDescription(badgeDto.getDescription());
		badgeDetail.setIcon(badgeDto.getIcon());
		badgeDetail.setTitle(badgeDto.getTitle());
		badgeDetail.setUpgradeBadgeId(badgeDto.getUpgradeBadgeId());
		badgeDetail.setUpgradeBadgeTitle(badgeDto.getUpgradeBadgeTitle());
		badgeDetail.setUpgradeRequiredTime(badgeDto.getUpgradeRequiredTimes());
		badgeDetail.setCompletedRequiredActivities(badgeDto.getCompletedRequiredActivities());
		badgeDetail.setAchievementTime(badgeDto.getAchievementTime());
		badgeDetail.setStatus(badgeDto.getStatus());
		return badgeDetail;
	}
}
