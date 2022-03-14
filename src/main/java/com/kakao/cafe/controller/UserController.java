package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.UserSaveDto;
import com.kakao.cafe.controller.dto.UserUpdateDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("UserSaveDto", new UserSaveDto());
        return "user/form";
    }

    @PostMapping
    public String create(@Valid UserSaveDto userSaveDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/form";
        }
        userService.save(userSaveDto);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = userService.findOne(userId);
        UserUpdateDto userUpdateDto = new UserUpdateDto(user);
        model.addAttribute("userUpdateDto", userUpdateDto);
        return "user/updateForm";
    }

    @PutMapping("/{userId}")
    public String update(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/updateForm";
        }
        userService.update(userUpdateDto);
        logger.info("user={}", userService.findOne(userUpdateDto.getUserId()));
        return "redirect:/users";
    }
}
