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
public class QRcodeController {
    @RequestMapping(value = {"/getQRCode"}, produces="image/jpeg")
    @ResponseBody
    public byte[] getOpenIdfromCode(@RequestParam(value = "activity_id", required = true) String activity_id) throws NumberFormatException, Exception {
		return LoginUtils.getQRCode(activity_id);
    }
}