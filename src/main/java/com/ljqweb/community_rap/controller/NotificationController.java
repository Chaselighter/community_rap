package com.ljqweb.community_rap.controller;

import com.ljqweb.community_rap.dto.NotificationDTO;
import com.ljqweb.community_rap.dto.PageinationDTO;
import com.ljqweb.community_rap.enums.NotificationTypeEnum;
import com.ljqweb.community_rap.model.Notification;
import com.ljqweb.community_rap.model.User;
import com.ljqweb.community_rap.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="id") Long id){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO =  notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
                ||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }

    }
}
