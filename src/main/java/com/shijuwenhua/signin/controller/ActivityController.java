package com.shijuwenhua.signin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shijuwenhua.signin.constant.StatusConstants;
import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;
import com.shijuwenhua.signin.service.ActivityBadgeService;
import com.shijuwenhua.signin.service.ActivityService;
import com.shijuwenhua.signin.service.BadgeService;
import com.shijuwenhua.signin.service.StorageService;
import com.shijuwenhua.signin.service.UserService;

@Controller
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private UserService userService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private ActivityBadgeService activityBadgeService;

	@Autowired
	private StorageService storageService;

	@RequestMapping("/getAllActivitys")
	@ResponseBody
	public List<Activity> getAllActivitys() {
		return activityService.getActivityList();
	}

	@RequestMapping("/createActivity")
	@ResponseBody
	public void createActivity(@RequestBody Activity activity, @RequestParam("activityId") long activityId) {
		activityService.save(activity);
	}

	@RequestMapping("/joinActivity")
	@ResponseBody
	public void joinActivity(@RequestParam("activityId") long activityId, @RequestParam("userId") long userId) {
		if (userService.findUserById(userId) == null) {
			User user = new User();
			user.setId(userId);
			userService.save(user);
		}
	}

	@RequestMapping("/deleteActivity/{id}")
	@ResponseBody
	public void deleteActivity(@PathVariable("id") Long id) {
		activityService.delete(id);
	}

	@RequestMapping("/listActivity")
	public String list(Model model) {
		List<Activity> activitys = activityService.getActivityList();
		model.addAttribute("activitys", activitys);
		return "activity/activityList";
	}

	@RequestMapping("/toAddActivity")
	public String toAdd(Model model) {
		List<Badge> badges = badgeService.getActivityEditBadgesList(0);
		model.addAttribute("badges", badges);
		return "activity/activityAdd";
	}

	@RequestMapping("/addActivity")
	public String add(Activity activity, long badgeTypeId, int requiredAttendTimes, String requiredActivity) throws Exception {
		activityService.save(activity);
		ActivityBadge activityBadge = new ActivityBadge();
		activityBadge.setActivityId(activity.getId());
		activityBadge.setBadgeId(badgeTypeId);
		activityBadge.setRequiredAttendTimes(requiredAttendTimes);
		activityBadge.setRequiredActivity(requiredActivity);
		activityBadgeService.save(activityBadge);
		badgeService.updateBadgeCoreActivities(badgeTypeId);
		return "redirect:/listActivity";
	}
	
	@RequestMapping("/addActivityWithPic")
	public String addActivityWithPic(Activity activity, long badgeTypeId, int requiredAttendTimes, String requiredActivity,
			@RequestParam("file") MultipartFile files) throws Exception {
		storageService.store(files, StatusConstants.ACTIVITY_ICON_STORE_LOCATION);
		activity.setIcon(files.getOriginalFilename());
		return add(activity, badgeTypeId, requiredAttendTimes, requiredActivity);
	}

	@RequestMapping("/toEditActivity")
	public String toEdit(Model model, Long id) {
		ActivityBadge activityBadge = activityBadgeService.findActivityBadgesByActivityId(id);
		Activity activity = activityService.findActivityById(id);
		model.addAttribute("activity", activity);
		Badge badge = badgeService.findBadgesByActivityId(id);
		if (badge == null) {
			badge = new Badge();
			badge.setId(0);
			badge.setTitle("");
			badge.setUpgradeBadgeId(0);
		}
		List<Badge> badges = badgeService.getActivityEditBadgesList(activityBadge.getBadgeId());
		model.addAttribute("editBadges", badges);
		model.addAttribute("badge", badge);
		model.addAttribute("activityBadge", activityBadge);
		return "activity/activityEdit";
	}

	// ALTER TABLE `dengdeng`.`badge` CHANGE COLUMN `description` `description`
	// VARCHAR(255) CHARACTER SET 'utf8' NOT NULL ;
	@RequestMapping("/editActivity")
	public String edit(Activity activity, long badgeTypeId, int requiredAttendTimes, String requiredActivity) {
		String iconLocation = activity.getIcon();
		String[] text = iconLocation.split("/");
		activity.setIcon(text[text.length-1]);
		activityService.edit(activity);
		ActivityBadge activityBadge = activityBadgeService.findActivityBadgesByActivityId(activity.getId());
		long beforeEditBadgeId = activityBadge.getBadgeId();
		String beforeRequiredActivity = activityBadge.getRequiredActivity();
		activityBadge.setBadgeId(badgeTypeId);
		activityBadge.setRequiredAttendTimes(requiredAttendTimes);
		activityBadge.setRequiredActivity(requiredActivity);
		activityBadgeService.edit(activityBadge);
		if (badgeTypeId != beforeEditBadgeId)
			badgeService.updateBadgeCoreActivities(badgeTypeId);
		if (!beforeRequiredActivity.equals(requiredActivity))
			badgeService.updateBadgeCoreActivities(activityBadge.getBadgeId());
		return "redirect:/listActivity";
	}

	@RequestMapping("/editActivityWithPic")
	public String editActivityWithPic(Activity activity, long badgeTypeId, int requiredAttendTimes,
			String requiredActivity, @RequestParam("file") MultipartFile files) throws Exception {
		storageService.store(files, StatusConstants.ACTIVITY_ICON_STORE_LOCATION);
		activity.setIcon(files.getOriginalFilename());
		return edit(activity, badgeTypeId, requiredAttendTimes, requiredActivity);
	}

	@RequestMapping("/deleteActivity")
	public String delete(Long id) {
		activityService.delete(id);
		activityBadgeService.delete(activityBadgeService.findActivityBadgesByActivityId(id).getId());
		return "redirect:/listActivity";
	}
}
