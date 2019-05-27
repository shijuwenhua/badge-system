package com.shijuwenhua.signin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shijuwenhua.signin.constant.StatusConstants;
import com.shijuwenhua.signin.mapper.DtoMapper;
import com.shijuwenhua.signin.model.ActivityBadge;
import com.shijuwenhua.signin.model.Badge;
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
import com.shijuwenhua.signin.utils.LoginUtils;

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

    @RequestMapping("/getOpenId")
    @ResponseBody
    public String getOpenIdfromCode(@RequestParam(value = "code", required = true) String code) {
		String openId = LoginUtils.getOpenId(code);
		if (openId != null){
			User user = userService.findUserByOpenId(openId);
			if (user == null){
				user = new User();
				user.setOpenId(openId);
				userService.save(user);
			}
		}
		return openId;
    }

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
    
    @RequestMapping("/getUserBadgesDetail/{openId}")
    @ResponseBody
    public List<BadgeDto> getUserBadgesDetail(@PathVariable("openId") String userOpenId) {
    	List<BadgeDto> badgeDtoList = new ArrayList<BadgeDto>();
    	List<Badge> badges= userService.findBadgesByUserId(userOpenId);
    	for (Badge badge : badges) {
    		BadgeDto badgeDto= dtoMapper.badgeMapper(badge);
    		badgeDto.setActivityList(activityBadgeService.findActivitiesByBadgeId(badge.getId()));
    		badgeDto.setBadgeList(badgeService.getSubBadgesList(badge.getId()));
    		badgeDtoList.add(badgeDto);
    	}
    	return badgeDtoList;
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
        List<User> users=userService.getUserList();
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
    
    @RequestMapping("/attendActivity/{openId}/{activityId}")
    @ResponseBody
    public String attendActivity(@PathVariable("openId") String userOpenId, @PathVariable("activityId") Long activityId) {
    	
    	Badge badge = activityBadgeService.findBadgesByActivityId(activityId);
    	if(badge == null)
    		return "Cannot find the activity related badge";
    	
    	User user = userService.findUserByOpenId(userOpenId);
    	UserActivity userActivity= new UserActivity();
    		
    	if(user == null) {
    		user = new User();
    		user.setOpenId(userOpenId);
    		user.setName(userOpenId);
    		userService.save(user);
    		userActivity = attendActivityAndBadge(userOpenId, activityId, badge);
    	} else {
    		userActivity = userActivityService.findByUserIdAndActivityId(activityId, userOpenId);
    		if(userActivity == null) {
    			userActivity = attendActivityAndBadge(userOpenId, activityId, badge);
    		} else {
    			userActivity.setAttendTimes(userActivity.getAttendTimes()+1);
    		}
    	}
    	if(StatusConstants.PROCESSING.equals(userActivity.getStatus())) {
	    	String activityStatus = activityService.checkActivityStatus(activityId, userActivity.getAttendTimes());
	    	userActivity.setStatus(activityStatus);
	    	checkBadge(badge.getId(), activityStatus, userOpenId);
    	}
    	userActivityService.save(userActivity);
    	
    	return "Attend the activity successfully";
    }

	private void checkBadge(long badgeId, String activityStatus, String userOpenId) {
		if(StatusConstants.COMPLETED.equals(activityStatus)) {
			UserBadge userBadge = userBadgeRepository.findByBadgeId(badgeId);
			if("Processing".equals(userBadge.getStatus())) {
				List<ActivityBadge> activityBadges = activityBadgeService.findActivityBadgesByBadgeId(badgeId);
				List<ActivityBadge> userCompletedActivityBadge = activityBadgeService.findActivityBadgesByBadgeIdAndUserOpenId(badgeId, userOpenId);
			}
		}
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
    	return userBadge;
    	
    }
    
}
