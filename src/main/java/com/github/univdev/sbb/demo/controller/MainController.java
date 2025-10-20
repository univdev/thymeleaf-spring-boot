package com.github.univdev.sbb.demo.controller;

import com.github.univdev.sbb.demo.entity.User;
import com.github.univdev.sbb.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    private String main(Model model) {
        Page<User> users = userService.getUsersSorted(0, 10, "id");

        model.addAttribute("message", "안녕하세요");
        model.addAttribute("users", users);
        return "index";
    }
}
