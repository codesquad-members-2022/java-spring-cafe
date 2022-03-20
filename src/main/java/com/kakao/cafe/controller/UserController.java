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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
        model.addAttribute("userSaveDto", new UserSaveDto());
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
    public String updateForm(@PathVariable String userId, HttpSession session, Model model, HttpServletResponse response) throws IOException {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        if (loginUser.isNotEqualsUserId(userId)) {
            response.sendError(403, "다른 사용자의 정보를 수정할 수 없습니다.");
        }

        model.addAttribute("userUpdateDto", new UserUpdateDto(loginUser));
        return "user/updateForm";
    }

    @PutMapping("/{userId}")
    public String update(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/updateForm";
        }

        boolean checkUpdate = userService.update(userUpdateDto);
        if (!checkUpdate) {
            bindingResult.reject("isNotCorrectUserInfo", "회원 정보가 일치하지 않습니다.");
            return "user/updateForm";
        }

        return "redirect:/users/" + userUpdateDto.getUserId();
    }
}
