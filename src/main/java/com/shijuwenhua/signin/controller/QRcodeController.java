package com.shijuwenhua.signin.controller;

import javax.annotation.Resource;

import com.shijuwenhua.signin.constant.StatusConstants;
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
    public byte[] getQRCode(@RequestParam(value = "activity_id", required = true) String activityId,
                                    @RequestParam(value = "activity_type", required = true) String activityType) throws NumberFormatException, Exception {
		boolean isComm =  StatusConstants.COMMON_SCRIPTURE.equals(activityType);
        return LoginUtils.getQRCode(activityId, isComm);
    }
}