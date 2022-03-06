package com.kakao.cafe.web;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.UserJoinDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String joinForm() {
        log.info("gettMapping joinForm");
        return "user/form";
    }

    @PostMapping("/form")
    public String join(UserJoinDto dto) {
        userService.userJoin(dto);
        log.info("postMapping join");
        log.info("userId = {}", dto.getUserId());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findUser(userId));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        log.info("updateForm id = {}", id);
        return "user/updateForm";
    }

    @PutMapping("/{id}/form")
    public String update(@PathVariable Long id, User user){
        log.info("update id = {} userName = {}",user.getId() ,user.getName());
        userService.userUpdate(id, user);
        return "redirect:/user/list";
    }
}
