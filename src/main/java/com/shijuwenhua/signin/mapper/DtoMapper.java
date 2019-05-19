package com.shijuwenhua.signin.mapper;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDto;

public class DtoMapper {

	public BadgeDto badgeMapper(Badge badge) {
		BadgeDto badgeDto = new BadgeDto();
		badgeDto.setId(badge.getId());
		badgeDto.setDescription(badge.getDescription());
		badgeDto.setIcon(badge.getIcon());
		badgeDto.setTitle(badge.getTitle());
		return badgeDto;
	}
}
