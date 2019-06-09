package com.shijuwenhua.signin.controller;

import javax.annotation.Resource;

import com.shijuwenhua.signin.service.UserActivityService;
import com.shijuwenhua.signin.service.UserService;
import com.shijuwenhua.signin.utils.LoginUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.model.User;
@Controller
public class LoginController {

	@Resource
	private UserService userService;
	
	@Resource
	private UserController userController;
	
	@Resource
	private UserActivityService userActivityService;

    @RequestMapping("/getOpenId")
    @ResponseBody
    public String getOpenIdfromCode(@RequestParam(value = "code", required = true) String code) throws NumberFormatException, Exception {
		String openId = LoginUtils.getOpenId(code);
		if (openId != null){
			User user = userService.findUserByOpenId(openId);
			if (user == null){
				user = new User();
				user.setOpenId(openId);
				userService.save(user);
				Badge defaultBadge = userController.checkIsExist(Long.parseLong("-1"));
				userActivityService.save(userController.attendActivityAndBadge(openId, Long.parseLong("-1"), defaultBadge));
			}
		}
		return openId;
    }
}