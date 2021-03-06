package com.ljqweb.community_rap.controller;

import com.ljqweb.community_rap.dto.NotificationDTO;
import com.ljqweb.community_rap.dto.PageinationDTO;
import com.ljqweb.community_rap.mapper.UserMapper;
import com.ljqweb.community_rap.model.Notification;
import com.ljqweb.community_rap.model.User;
import com.ljqweb.community_rap.service.NotificationService;
import com.ljqweb.community_rap.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="action") String action,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size
                          ){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            return "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            PageinationDTO pageinationDTO = questionService.list(user.getId(),page,size);
            model.addAttribute("pageination",pageinationDTO);
        }else if("replies".equals(action)){

            PageinationDTO pageinationDTO = notificationService.list(user.getId(),page,size);

            model.addAttribute("section","replies");
            model.addAttribute("pageination",pageinationDTO);

            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
