package com.shijuwenhua.signin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.shijuwenhua.signin.constant.StatusConstants;
import com.shijuwenhua.signin.mapper.DtoMapper;
import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.model.ActivityDto;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.BadgeDetail;
import com.shijuwenhua.signin.model.BadgeDto;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.model.UserActivity;
import com.shijuwenhua.signin.model.UserBadge;
import com.shijuwenhua.signin.repository.UserBadgeRepository;
import com.shijuwenhua.signin.service.ActivityBadgeService;
import com.shijuwenhua.signin.service.ActivityService;
import com.shijuwenhua.signin.service.BadgeService;
import com.shijuwenhua.signin.service.UserActivityService;
import com.shijuwenhua.signin.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@Resource
	private ActivityService activityService;

	@Resource
	private UserActivityService userActivityService;

	@Resource
	private ActivityBadgeService activityBadgeService;

	@Autowired
	private UserBadgeRepository userBadgeRepository;

	@Autowired
	private BadgeService badgeService;

	private DtoMapper dtoMapper = new DtoMapper();

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");

	private int retryTimes = 0;

	@RequestMapping("/getAllUsers")
	@ResponseBody
	public List<User> getAllUsers() {
		return userService.getUserList();
	}

	@RequestMapping("/getUpgradeBadges/{badgeId}")
	@ResponseBody
	public Badge getUpgradeBadges(@PathVariable("badgeId") long badgeId) {
		return badgeService.getUpgradeBadge(badgeId);
	}

	@RequestMapping("/getUserActivty/{userOpenId}/{activityId}")
	@ResponseBody
	public ActivityDto getUserActivty(@PathVariable("activityId") long activityId,
			@PathVariable("userOpenId") String userOpenId) {
		return activityService.findUserActivityDto(activityId, userOpenId);
	}

	@RequestMapping("/getUserBadgesDetailList/{userOpenId}")
	@ResponseBody
	public List<BadgeDetail> getUserBadgesDetailList(@PathVariable("userOpenId") String userOpenId) {
		List<BadgeDetail> badgeDtoList = new ArrayList<BadgeDetail>();
		List<BadgeDto> badges = userService.findBadgesByUserId(userOpenId);
		for (BadgeDto badgeDto : badges) {
			BadgeDetail badgeDetail = getBadgeDetail(userOpenId, badgeDto);
			badgeDtoList.add(badgeDetail);
		}
		return badgeDtoList;
	}

	@RequestMapping("/getUserBadgesDetail/{userOpenId}/{badgeId}")
	@ResponseBody
	public BadgeDetail getUserBadgesDetail(@PathVariable("userOpenId") String userOpenId,
			@PathVariable("badgeId") long badgeId) {
		return getBadgeDetail(userOpenId, userService.findBadgesByUserId(badgeId, userOpenId));
	}

	private BadgeDetail getBadgeDetail(String userOpenId, BadgeDto badgeDto) {
		BadgeDetail badgeDetail = dtoMapper.badgeMapper(badgeDto);
		List<ActivityDto> activityList = getUserActivities(badgeDto, userOpenId);
		badgeDetail.setUserActivityList(activityList);
		List<BadgeDto> subBadgeList = getUserSubBadges(badgeDto, userOpenId);
		badgeDetail.setBadgeList(subBadgeList);
		return badgeDetail;
	}

	private List<ActivityDto> getUserActivities(BadgeDto badgeDto, String userOpenId) {
		List<ActivityDto> userActivities = userActivityService.findActivitiesByBadgeId(badgeDto.getId(), userOpenId,
				StatusConstants.QUERY_STATUS_ALL);
		List<ActivityDto> badgeActivities = activityService.findActivitiesDtoByBadgeId(badgeDto.getId());
		for (ActivityDto userActivity : userActivities) {
			for (int n = 0; n < badgeActivities.size(); n++) {
				if (userActivity.getId() == badgeActivities.get(n).getId()) {
					// Getting commonTotalAttend from user activity record is not real-time (only the last updated user will get correct number)
					// So get the number from commScripture record
					if (StatusConstants.COMMON_SCRIPTURE.equals(userActivity.getType())) {
						UserActivity commonUserActivity = userActivityService.findByUserIdAndActivityId(userActivity.getId(),
								StatusConstants.COMMON_SCRIPTURE);
						userActivity.setCommonTotalAttend(commonUserActivity.getAttendTimes());
					}
					badgeActivities.set(n, userActivity);
				}
			}
		}
		return badgeActivities;
	}

	private List<BadgeDto> getUserSubBadges(BadgeDto badgeDto, String userOpenId) {
		List<BadgeDto> allSubBadges = badgeService.getSubBadgesList(badgeDto.getId());
		List<BadgeDto> userSubBadges = badgeService.getUserSubBadgesList(badgeDto.getId(), userOpenId);
		for (BadgeDto userSubBadge : userSubBadges) {
			for (int n = 0; n < allSubBadges.size(); n++) {
				if (userSubBadge.getId() == allSubBadges.get(n).getId()) {
					allSubBadges.set(n, userSubBadge);
				}
			}
		}
		return allSubBadges;
	}

	@RequestMapping("/deleteUser/{id}")
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		userService.delete(id);
	}

	@RequestMapping("/createUser")
	@ResponseBody
	public void createUser(User user) {
		userService.save(user);
	}

	@RequestMapping("/")
	public String index() {
		return "redirect:/list";
	}

	@RequestMapping("/list")
	public String list(Model model) {
		List<User> users = userService.getUserList();
		model.addAttribute("users", users);
		return "user/list";
	}

	@RequestMapping("/toAdd")
	public String toAdd() {
		return "user/userAdd";
	}

	@RequestMapping("/add")
	public String add(User user) {
		userService.save(user);
		return "redirect:/list";
	}

	@RequestMapping("/joinActivity/{openId}/{activityId}")
	@ResponseBody
	public List<BadgeDetail> joinActivity(@PathVariable("openId") String userOpenId,
			@PathVariable("activityId") Long activityId) throws Exception {

		Badge badge = checkIsExist(activityId).getFirst();

		userActivityService.save(checkAndCreateUserActivity(userOpenId, activityId, badge.getId()));

		return getUserBadgesDetailList(userOpenId);
	}

	@RequestMapping("/attendActivityReutrnBadgeDetail/{openId}/{activityId}/{attendTimes}")
	@ResponseBody
	public BadgeDetail attendActivityReutrnBadgeDetail(@PathVariable("openId") String userOpenId,
			@PathVariable("activityId") Long activityId, @PathVariable("attendTimes") int attendTimes,
			@RequestParam(value = "comments", required = true) String comments) throws Exception {

		Badge badge = attendActivity(userOpenId, activityId, attendTimes, comments);

		return getUserBadgesDetail(userOpenId, badge.getId());
	}

	@RequestMapping("/attendActivityReutrnActivityDetail/{openId}/{activityId}/{attendTimes}")
	@ResponseBody
	public ActivityDto attendActivityReutrnActivityDetail(@PathVariable("openId") String userOpenId,
			@PathVariable("activityId") Long activityId, @PathVariable("attendTimes") int attendTimes,
			@RequestParam(value = "comments", required = true) String comments) throws Exception {

		attendActivity(userOpenId, activityId, attendTimes, comments);

		return getUserActivty(activityId, userOpenId);
	}

	private Badge attendActivity(String userOpenId, Long activityId, int attendTimes, String comments)
			throws Exception {
		Pair<Badge, String> checkResult = checkIsExist(activityId);
		Badge badge = checkResult.getFirst();

		UserActivity userActivity = checkAndCreateUserActivity(userOpenId, activityId, badge.getId());

		if (StringUtils.equals(StatusConstants.COMMON_SCRIPTURE, checkResult.getSecond())) {
			userActivity.setAttendTimes(userActivity.getAttendTimes() + attendTimes);
			userActivity.setComments(comments);
			UserActivity commonActivity = commonActivity(userOpenId, activityId, badge, attendTimes);
			if(commonActivity != null) {
				userActivity.setCommonTotalAttend(commonActivity.getAttendTimes());
				userActivity.setStatus(commonActivity.getStatus());
			} else {
				userActivity.setCommonTotalAttend(0);
			}
			if(StatusConstants.COMPLETED.equals(userActivity.getStatus())) {
				String achievementTime = LocalDateTime.now().format(formatter);
				userActivity.setAchievementTime(achievementTime);
			}
			userActivityService.save(userActivity);
		} else {
			normalActivity(userOpenId, activityId, badge, userActivity, attendTimes, false);
		}
		return badge;
	}

	private UserActivity commonActivity(String userOpenId, Long activityId, Badge badge, int attendTimes) {
		try {
			UserActivity commonUserActivity = userActivityService.findByUserIdAndActivityId(activityId,
					StatusConstants.COMMON_SCRIPTURE);
			int comTotalAttend = commonUserActivity.getAttendTimes() + attendTimes;
			if (StatusConstants.PROCESSING.equals(commonUserActivity.getStatus())) {
				normalActivity(userOpenId, activityId, badge, commonUserActivity, attendTimes, true);
			} else {
				commonUserActivity.setAttendTimes(comTotalAttend);
				userActivityService.save(commonUserActivity);
			}
			return commonUserActivity;
		} catch (Exception ex) {
			logger.error("Error in update Common Activity. Start retry update the Common Activity.");
			if (retryTimes < 1) {
				retryTimes++;
				return commonActivity(userOpenId, activityId, badge, attendTimes);
			}
		}
		return null;

	}

	private void normalActivity(String userOpenId, Long activityId, Badge badge, UserActivity userActivity,
			int attendTimes, boolean isCommonActivity) {
		if (StatusConstants.PROCESSING.equals(userActivity.getStatus())) {
			userActivity.setAttendTimes(userActivity.getAttendTimes() + attendTimes);
			String activityStatus = activityService.checkActivityStatus(activityId, userActivity.getAttendTimes());
			userActivity.setStatus(activityStatus);
			if (StatusConstants.COMPLETED.equals(activityStatus)) {
				updateActivityAndBadge(userOpenId, activityId, badge, userActivity, isCommonActivity, activityStatus);
			} else {
				userActivityService.save(userActivity);
			}
		} else {
			userActivityService.save(userActivity);
		}
	}

	private void updateActivityAndBadge(String userOpenId, Long activityId, Badge badge, UserActivity userActivity,
			boolean isCommonActivity, String activityStatus) {
		String achievementTime = LocalDateTime.now().format(formatter);
		userActivity.setAchievementTime(achievementTime);
		if (isCommonActivity) {
//			 userActivityService.updateCommonActivity(userActivity);
			userActivityService.update(userActivity);
			try {
				userBadgeRepository.updateCommonUserBadges(badge.getId(), StatusConstants.COMPLETED, achievementTime);
				userActivityService.updateUserCommonActivities(activityId, StatusConstants.COMPLETED, achievementTime);
			} catch (Exception ex) {
				logger.error("Error in update all user common Activity and Badge. Retry the proccess");
				if (retryTimes < 2) {
					retryTimes++;
					updateActivityAndBadge(userOpenId, activityId, badge, userActivity, isCommonActivity,
							activityStatus);
				}
			}
		} else {
			userActivityService.update(userActivity);
			checkBadge(badge, activityStatus, userOpenId);
		}
	}

	public UserActivity checkAndCreateUserActivity(String userOpenId, long activityId, long badgeId) throws Exception {
		User user = userService.findUserByOpenId(userOpenId);
		UserActivity userActivity = new UserActivity();

		if (user == null) {
			user = new User();
			user.setOpenId(userOpenId);
			user.setName(userOpenId);
			userService.save(user);
			if (activityId != Long.parseLong("-1")) {
				Badge defaultBadge = checkIsExist(Long.parseLong("-1")).getFirst();
				userActivityService
						.save(attendActivityAndBadge(userOpenId, Long.parseLong("-1"), defaultBadge.getId()));
			}
			userActivity = attendActivityAndBadge(userOpenId, activityId, badgeId);
		} else {
			userActivity = userActivityService.findByUserIdAndActivityId(activityId, userOpenId);
			if (userActivity == null) {
				userActivity = attendActivityAndBadge(userOpenId, activityId, badgeId);
			}
		}
		return userActivity;
	}

	public Pair<Badge, String> checkIsExist(Long activityId) throws Exception {
		Activity activity = activityService.findActivityById(activityId);
		if (activity == null)
			throw new Exception("Cannot find the activity");
		Badge badge = badgeService.findBadgesByActivityId(activityId);
		if (badge == null)
			throw new Exception("Cannot find the activity related badge");
		return Pair.of(badge, activity.getType());
	}

	private void checkBadge(Badge badge, String activityStatus, String userOpenId) {
		UserBadge userBadge = userBadgeRepository.findByBadgeIdAndUserId(badge.getId(), userOpenId);
		if ("Processing".equals(userBadge.getStatus())) {
			if (isObtainBadge(badge, userOpenId)) {
				userBadge.setStatus(StatusConstants.COMPLETED);
				userBadge.setAchievementTime(LocalDateTime.now().format(formatter));
				userBadgeRepository.save(userBadge);
				checkUpgradeBadge(badge.getId(), userOpenId);
			}
		}
	}

	private void checkUpgradeBadge(long badgeId, String userOpenId) {
		Badge upgradeBadge = badgeService.getUpgradeBadge(badgeId);
		if (upgradeBadge != null) {
			UserBadge userBadge = userBadgeRepository.findByBadgeIdAndUserId(upgradeBadge.getId(), userOpenId);
			if (userBadge == null) {
				userBadge = createUserBadge(userOpenId, upgradeBadge.getId());
			}
			int obtainNum = userBadge.getObtainTimes() + 1;
			int upgradeRequiredTimes = upgradeBadge.getUpgradeRequiredTimes();
			if (obtainNum >= upgradeRequiredTimes) {
				userBadge.setObtainTimes(obtainNum - upgradeRequiredTimes);
				checkUpgradeBadge(upgradeBadge.getId(), userOpenId);
			}
			userBadgeRepository.save(userBadge);
		}
	}

	private boolean isObtainBadge(Badge badge, String userOpenId) {
		// List<ActivityBadge> activityBadges =
		// activityBadgeService.findActivityBadgesByBadgeId(badge.getId());
		List<ActivityBadge> userCompletedActivityBadge = activityBadgeService.findUserCompletedActivity(badge.getId(),
				userOpenId);
		if (userCompletedActivityBadge.size() >= badge.getCompletedRequiredActivities()) {
			// int allRequiredActivities = 0;
			int userCompletedRequired = 0;
			// allRequiredActivities = countRequiredActivities(activityBadges);
			userCompletedRequired = countRequiredActivities(userCompletedActivityBadge);
			if (userCompletedRequired >= 0 && userCompletedRequired >= badge.getCoreActivities()) {
				return true;
			}
		}
		return false;
	}

	private int countRequiredActivities(List<ActivityBadge> activityBadges) {
		int count = 0;
		for (ActivityBadge activityBadge : activityBadges) {
			if ("yes".equals(activityBadge.getRequiredActivity())) {
				count += 1;
			}
		}
		return count;
	}

	public UserActivity attendActivityAndBadge(String userOpenId, Long activityId, long badgeId) {
		UserActivity userActivity;
		userActivity = createUserActivity(userOpenId, activityId);
		if (userBadgeRepository.findByBadgeIdAndUserId(badgeId, userOpenId) == null)
			userBadgeRepository.save(createUserBadge(userOpenId, badgeId));
		return userActivity;
	}

	private UserActivity createUserActivity(String userOpenId, long activityId) {
		UserActivity userActivity = new UserActivity();
		userActivity.setActivityId(activityId);
		userActivity.setUserOpenId(userOpenId);
		userActivity.setStatus(StatusConstants.PROCESSING);
		userActivity.setAttendTimes(0);
		return userActivity;
	}

	private UserBadge createUserBadge(String userOpenId, long badgeId) {
		UserBadge userBadge = new UserBadge();
		userBadge.setBadgeId(badgeId);
		userBadge.setStatus("Processing");
		userBadge.setUserOpenId(userOpenId);
		userBadge.setObtainTimes(0);
		return userBadge;
	}
}
