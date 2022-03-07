package com.kakao.cafe.web.users;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.validation.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidation userValidation;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidation);
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findUsers();
        log.info("users list = {}", users);
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/create")
    public String addForm(Model model) {
        log.info("create form 접근");
        model.addAttribute("user", new User());
        return "/user/form";
    }

    @PostMapping("/create")
    public String saveUser(@Validated @ModelAttribute User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "/user/form";
        }

        userService.register(user);
        log.info("save user = {}", user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        User findUser = userService.findUserById(userId);
        log.info("user profile = {}", findUser);
        model.addAttribute("user", findUser);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model){
        User findUser = userService.findUserById(userId);
        log.info("get profile update form");
        model.addAttribute("user", findUser);
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateProfile(@ModelAttribute User user){
        userService.userUpdate(user);
        return "redirect:/users";
    }
}
