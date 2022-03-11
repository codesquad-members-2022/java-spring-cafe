package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserForm;
import com.kakao.cafe.service.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        User user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/form")
    public String formCreateUser() {
        return "user/form";
    }

    @PostMapping
    public ModelAndView createUser(UserForm userForm, HttpSession session) {
        User user = userService.register(userForm);
        session.setAttribute("loginUser", user);

        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/{userId}/form")
    public String formUpdateUser(@PathVariable String userId, Model model) {
        User user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/update_form";
    }

    @PutMapping("/{userId}")
    public ModelAndView updateUser(@PathVariable String userId, UserForm userForm) {
        userForm.setUserId(userId);
        User user = userService.updateUser(userForm);

        ModelAndView mav = new ModelAndView("redirect:/users");
        mav.addObject("user", user);
        return mav;
    }

}
