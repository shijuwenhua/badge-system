package com.shijuwenhua.signin.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shijuwenhua.signin.model.Badge;
import com.shijuwenhua.signin.service.BadgeService;

@Controller
public class BadgeController {

	@Autowired
    private BadgeService badgeService;

    @RequestMapping("/getAllBadges")
    @ResponseBody
    public List<Badge> getAllBadges() {
        return badgeService.getAllBadges();
    }
    
    @RequestMapping("/getAllSubBadges/{id}")
    @ResponseBody
    public List<Badge> getAllSubBadges(@PathVariable("id") Long id) {
    	ConcurrentHashMap<Long, List<Badge>> allSubBadges = new ConcurrentHashMap<Long, List<Badge>>();
    	Badge badge = badgeService.findBadgeById(id);
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
        List<Badge> badges =badgeService.getAllBadges();
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
    public String add(Badge badge) {
        badgeService.save(badge);
        return "redirect:/listBadge";
    }
    
    @RequestMapping("/toEditBadge")
    public String toEdit(Model model,Long id) {
        Badge badge=badgeService.findBadgeById(id);
        List<Badge> editBadgesList = badgeService.getEditBadgeList(id);
    	model.addAttribute("editBadges", editBadgesList);
        model.addAttribute("badge", badge);
        model.addAttribute("highLevel", badge.getCompletedRequiredActivities()==0?true:false);
        return "badge/badgeEdit";
    }

    @RequestMapping("/editBadge")
    public String edit(Badge Badge) {
    	badgeService.edit(Badge);
        return "redirect:/listBadge";
    }

    @RequestMapping("/deleteBadge")
    public String delete(Long id) {
        badgeService.delete(id);
        return "redirect:/listBadge";
    }
}
