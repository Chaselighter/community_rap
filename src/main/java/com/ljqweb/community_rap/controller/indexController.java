package com.ljqweb.community_rap.controller;


import com.ljqweb.community_rap.dto.PageinationDTO;
import com.ljqweb.community_rap.dto.QuestionDTO;
import com.ljqweb.community_rap.mapper.QuestionMapper;
import com.ljqweb.community_rap.mapper.UserMapper;
import com.ljqweb.community_rap.model.Question;
import com.ljqweb.community_rap.model.User;
import com.ljqweb.community_rap.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size){


        PageinationDTO pageination=questionService.List(page,size);
        model.addAttribute("pageination",pageination);

        return "index";
    }
}
