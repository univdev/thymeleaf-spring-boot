package com.github.univdev.sbb.demo.controller;

import com.github.univdev.sbb.demo.dto.UserForm;
import com.github.univdev.sbb.demo.entity.User;
import com.github.univdev.sbb.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 생성 폼 보여주기
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/form";
    }

    // 생성 처리
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute UserForm userForm,
                         BindingResult bindingResult,
                         Model model) {

        // 유효성 검사 실패 시 폼으로 돌아가기 (데이터 유지됨)
        if (bindingResult.hasErrors()) {
            return "users/form";
        }

        // 비즈니스 로직 검증 (예: 이메일 중복 체크)
        if (userService.existsByEmail(userForm.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "이미 사용 중인 이메일입니다");
            return "users/form";
        }

        // 저장
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAge(userForm.getAge());
        userService.createUser(user);

        return "redirect:/users";
    }

    // 수정 폼 보여주기
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);

        // Entity를 DTO로 변환
        UserForm userForm = new UserForm();
        userForm.setId(user.getId());
        userForm.setName(user.getName());
        userForm.setEmail(user.getEmail());
        userForm.setAge(user.getAge());

        model.addAttribute("userForm", userForm);
        return "users/form";
    }

    // 수정 처리
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute UserForm userForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/form";  // 에러 시 입력값 유지하며 폼으로 돌아감
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAge(userForm.getAge());
        userService.updateUser(id, user);

        return "redirect:/users";
    }
}