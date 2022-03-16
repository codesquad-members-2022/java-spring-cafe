package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newForm() {
        return "user/form";
    }

    @PostMapping("/new")
    public String signUp(@ModelAttribute User user) {
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("{userId}/update")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PutMapping("{userId}/update")
    public String updateUser(@PathVariable String userId, @ModelAttribute User user) {
        User before = userService.findOne(userId);
        log.info("before userId={}, name={}, email={}", before.getUserId(), before.getName(), before.getEmail());
        log.info("input  userId={}, name={}, email={}", user.getUserId(), user.getName(), user.getEmail());

        userService.updateUser(userId, user);

        User after = userService.findOne(userId);
        log.info("after  userId={}, name={}, email={}", after.getUserId(), after.getName(), after.getEmail());

        return "redirect:/users";
    }
}
