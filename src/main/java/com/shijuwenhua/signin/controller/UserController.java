package com.shijuwenhua.signin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shijuwenhua.signin.constant.StatusConstants;
import com.shijuwenhua.signin.mapper.DtoMapper;
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

	@RequestMapping("/getUserBadgesDetailList/{userOpenId}")
	@ResponseBody
	public List<BadgeDetail> getUserBadgesDetailList(@PathVariable("userOpenId") String userOpenId) {
		List<BadgeDetail> badgeDtoList = new ArrayList<BadgeDetail>();
		List<Badge> badges = userService.findBadgesByUserId(userOpenId);
		for (Badge badge : badges) {
			BadgeDetail badgeDetail = getBadgeDetail(userOpenId, badge);
			badgeDtoList.add(badgeDetail);
		}
		return badgeDtoList;
	}

	@RequestMapping("/getUserBadgesDetail/{userOpenId}/{badgeId}")
	@ResponseBody
	public BadgeDetail getUserBadgesDetail(@PathVariable("userOpenId") String userOpenId,
			@PathVariable("badgeId") long badgeId) {
		return getBadgeDetail(userOpenId, badgeService.findBadgeById(badgeId));
	}

	private BadgeDetail getBadgeDetail(String userOpenId, Badge badge) {
		BadgeDetail badgeDetail = dtoMapper.badgeMapper(badge);
		List<ActivityDto> activityList = getUserActivities(badge, userOpenId);
		badgeDetail.setUserActivityList(activityList);
		List<BadgeDto> subBadgeList = getUserSubBadges(badge, userOpenId);
		badgeDetail.setBadgeList(subBadgeList);
		return badgeDetail;
	}

	private List<ActivityDto> getUserActivities(Badge badge, String userOpenId) {
		List<ActivityDto> userActivities = userActivityService.findActivitiesByBadgeId(badge.getId(), userOpenId,
				StatusConstants.QUERY_STATUS_ALL);
		List<ActivityDto> badgeActivities = activityService.findActivitiesDtoByBadgeId(badge.getId());
		for (ActivityDto userActivity : userActivities) {
			for (int n = 0; n < badgeActivities.size(); n++) {
				if (userActivity.getId() == badgeActivities.get(n).getId()) {
					badgeActivities.set(n, userActivity);
				}
			}
		}
		return badgeActivities;
	}

	private List<BadgeDto> getUserSubBadges(Badge badge, String userOpenId) {
		List<BadgeDto> allSubBadges = badgeService.getSubBadgesList(badge.getId());
		List<BadgeDto> userSubBadges = badgeService.getUserSubBadgesList(badge.getId(), userOpenId);
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
		
		Badge badge = checkIsExist(activityId);

		checkAndCreateUserActivity(userOpenId, activityId, badge);
		
		return getUserBadgesDetailList(userOpenId);
	}
	
	@RequestMapping("/attendActivity/{openId}/{activityId}")
	@ResponseBody
	public BadgeDetail attendActivity(@PathVariable("openId") String userOpenId,
			@PathVariable("activityId") Long activityId) throws Exception {

		Badge badge = checkIsExist(activityId);

		UserActivity userActivity = checkAndCreateUserActivity(userOpenId, activityId, badge);
		
		userActivity.setAttendTimes(userActivity.getAttendTimes() + 1);
		
		if (StatusConstants.PROCESSING.equals(userActivity.getStatus())) {
			String activityStatus = activityService.checkActivityStatus(activityId, userActivity.getAttendTimes());
			userActivity.setStatus(activityStatus);
			if (StatusConstants.COMPLETED.equals(activityStatus)) {
				userActivity.setAchievementTime(LocalDateTime.now().format(formatter));
				checkBadge(badge, activityStatus, userOpenId);
			}
		}
		userActivityService.save(userActivity);

		return getUserBadgesDetail(userOpenId, badge.getId());
	}

	private UserActivity checkAndCreateUserActivity(String userOpenId, Long activityId, Badge badge) {
		User user = userService.findUserByOpenId(userOpenId);
		UserActivity userActivity = new UserActivity();

		if (user == null) {
			user = new User();
			user.setOpenId(userOpenId);
			user.setName(userOpenId);
			userService.save(user);
			userActivity = attendActivityAndBadge(userOpenId, activityId, badge);
		} else {
			userActivity = userActivityService.findByUserIdAndActivityId(activityId, userOpenId);
			if (userActivity == null) {
				userActivity = attendActivityAndBadge(userOpenId, activityId, badge);
			}
		}
		return userActivity;
	}

	private Badge checkIsExist(Long activityId) throws Exception {
		if(activityService.findActivityById(activityId) == null)
			throw new Exception("Cannot find the activity");
		Badge badge = badgeService.findBadgesByActivityId(activityId);
		if (badge == null)
			throw new Exception("Cannot find the activity related badge");
		return badge;
	}

	private void checkBadge(Badge badge, String activityStatus, String userOpenId) {
		UserBadge userBadge = userBadgeRepository.findByBadgeId(badge.getId(), userOpenId);
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
			UserBadge userBadge = userBadgeRepository.findByBadgeId(badgeId, userOpenId);
			if (userBadge == null) {
				userBadge = createUserBadge(userOpenId, badgeId);
			}
			int obtainNum = userBadge.getObtainTimes() + 1;
			int upgradeRequiredTimes = upgradeBadge.getUpgradeRequiredTimes();
			if (obtainNum >= upgradeRequiredTimes) {
				obtainNum -= upgradeRequiredTimes;
				checkUpgradeBadge(badgeId, userOpenId);
			}
			userBadgeRepository.save(userBadge);
		}
	}

	private boolean isObtainBadge(Badge badge, String userOpenId) {
		List<ActivityBadge> activityBadges = activityBadgeService.findActivityBadgesByBadgeId(badge.getId());
		List<ActivityBadge> userCompletedActivityBadge = activityBadgeService.findUserCompletedActivity(badge.getId(),
				userOpenId);
		if (userCompletedActivityBadge.size() >= badge.getCompletedRequiredActivities()) {
			int allRequiredActivities = 0;
			int userCompletedRequired = 0;
			allRequiredActivities = countRequiredActivities(activityBadges);
			userCompletedRequired = countRequiredActivities(userCompletedActivityBadge);
			if (userCompletedRequired > 0 && userCompletedRequired == allRequiredActivities) {
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

	private UserActivity attendActivityAndBadge(String userOpenId, Long activityId, Badge badge) {
		UserActivity userActivity;
		userActivity = createUserActivity(userOpenId, activityId);
		userBadgeRepository.save(createUserBadge(userOpenId, badge.getId()));
		return userActivity;
	}

	private UserActivity createUserActivity(String userOpenId, long activityId) {
		UserActivity userActivity = new UserActivity();
		userActivity.setActivityId(activityId);
		userActivity.setUserOpenId(userOpenId);
		userActivity.setStatus(StatusConstants.PROCESSING);
		userActivity.setAttendTimes(1);
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
