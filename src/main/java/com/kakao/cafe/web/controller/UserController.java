package com.kakao.cafe.web.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.user.LoginUserDto;
import com.kakao.cafe.web.dto.user.UserDto;
import com.kakao.cafe.web.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String newForm(@ModelAttribute(name="user") UserDto userDto) {
        return "user/form";
    }

    @PostMapping("/new")
    public String signUp(@Validated @ModelAttribute(name = "user") UserDto userDto,
                         BindingResult bindingResult) {
        if (userService.findOne(userDto.getUserId()) != null) {
            bindingResult.rejectValue("userId","duplicatedUserId");
            log.info("errors={}", bindingResult);
            return "user/form";
        }

        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        User signUpUser = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        userService.signUp(signUpUser);
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

    @GetMapping("/login")
    public String loginForm(@ModelAttribute(name="user") LoginUserDto loginUserDto) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "user") LoginUserDto loginUserDto,
                        BindingResult bindingResult, HttpSession httpSession) {

        // field error 출력
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        User loginUser = userService.login(loginUserDto.getUserId(), loginUserDto.getPassword());

        // global error 출력
        if (loginUser == null) {
            bindingResult.reject("loginFailed");
            return "user/login";
        }

        httpSession.setAttribute(SessionConst.LOGIN_SESSION_NAME, loginUser);

        return "index";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";

    }
}
