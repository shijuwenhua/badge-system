package com.shijuwenhua.signin.mapper;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDetail;

public class DtoMapper {

	public BadgeDetail badgeMapper(Badge badge) {
		BadgeDetail badgeDto = new BadgeDetail();
		badgeDto.setId(badge.getId());
		badgeDto.setDescription(badge.getDescription());
		badgeDto.setIcon(badge.getIcon());
		badgeDto.setTitle(badge.getTitle());
		badgeDto.setUpgradeBadgeId(badge.getUpgradeBadgeId());
		badgeDto.setUpgradeBadgeTitle(badge.getUpgradeBadgeTitle());
		badgeDto.setUpgradeRequiredTime(badge.getUpgradeRequiredTimes());
		badgeDto.setCompletedRequiredActivities(badge.getCompletedRequiredActivities());
		return badgeDto;
	}
}
