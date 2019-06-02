package com.shijuwenhua.signin.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.service.BadgeService;
import com.shijuwenhua.signin.service.StorageService;

@Controller
public class BadgeController {

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private StorageService storageService;

	@RequestMapping("/getAllBadges")
	@ResponseBody
	public List<Badge> getAllBadges() {
		return badgeService.getAllBadges();
	}

	@RequestMapping("/getAllSubBadges/{id}")
	@ResponseBody
	public List<Badge> getAllSubBadges(@PathVariable("id") Long id) {
		ConcurrentHashMap<Long, List<Badge>> allSubBadges = new ConcurrentHashMap<Long, List<Badge>>();
		List<Badge> badges = badgeService.getSubBadges(id);
		allSubBadges.put(id, badges);
		return badges;
	}

	@RequestMapping("/createBadge")
	@ResponseBody
	public void createBadge(@RequestBody Badge badge) {
		badgeService.save(badge);
	}

	@RequestMapping("/deleteBadge/{id}")
	@ResponseBody
	public void deleteBadge(@PathVariable("id") Long id) {
		badgeService.delete(id);
	}

	@RequestMapping("/listBadge")
	public String list(Model model) {
		List<Badge> badges = badgeService.getAllBadges();
		model.addAttribute("badges", badges);
		return "badge/badgeList";
	}

	@RequestMapping("/toAddBadge")
	public String toAdd(Model model) {
		List<Badge> badges = badgeService.getHighBadges();
		model.addAttribute("badges", badges);
		return "badge/badgeAdd";
	}

	@RequestMapping("/addBadge")
	public String add(Badge badge) throws Exception {
		badgeService.save(badge);
		return "redirect:/listBadge";
	}
	
	@RequestMapping("/addBadgeWithPic")
	public String addWithPic(Badge badge, @RequestParam("file") MultipartFile files) throws Exception {
		storageService.store(files, StatusConstants.BADGE_ICON_STORE_LOCATION);
		badge.setIcon(files.getOriginalFilename());
		return add(badge);
	}

	@RequestMapping("/toEditBadge")
	public String toEdit(Model model, Long id) {
		Badge badge = badgeService.findBadgeById(id);
		if (badge == null) {
			badge = new Badge();
			badge.setId(0);
			badge.setTitle("");
			badge.setUpgradeBadgeId(0);
		}
		List<Badge> editBadgesList = badgeService.getEditBadgeList(id, badge.getUpgradeBadgeId());
		model.addAttribute("editBadges", editBadgesList);
		model.addAttribute("badge", badge);
		model.addAttribute("highLevel", badge.getCompletedRequiredActivities() == 0 ? true : false);
		return "badge/badgeEdit";
	}

	@RequestMapping("/editBadge")
	public String edit(Badge badge) {
		String iconLocation = badge.getIcon();
		String[] text = iconLocation.split("/");
		badge.setIcon(text[text.length-1]);
		badgeService.edit(badge);
		return "redirect:/listBadge";
	}
	
	@RequestMapping("/editBadgeWithPic")
	public String editBadgeWithPic(Badge badge, @RequestParam("file") MultipartFile files) throws Exception{
		storageService.store(files, StatusConstants.BADGE_ICON_STORE_LOCATION);
		badge.setIcon(files.getOriginalFilename());
		return edit(badge);
	}

	@RequestMapping("/deleteBadge")
	public String delete(Long id) {
		badgeService.delete(id);
		return "redirect:/listBadge";
	}
}
