package com.kaka.house.controller;

import com.kaka.house.common.model.User;
import com.kaka.house.mapper.UserMapper;
import com.kaka.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("hello")
    public String getUsers(ModelMap modelMap){
        List<User> users = userService.getUsers();
        User user = users.get(0);
        modelMap.put("user", user);
        return "hello";
    }

    @RequestMapping("index")
    public String index(){
        return "homepage/index";
    }
}
