package com.kakao.cafe.web;

import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.dto.LoginDto;
import com.kakao.cafe.web.dto.UserDto;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/sign-up")
    public String joinForm() {
        logger.info("User in joinForm");
        return "user/form";
    }

    @PostMapping("/user/sign-up")
    public String joinUser(@Valid UserDto userDto) {
        logger.info("[{}] sign-up", userDto);
        userService.join(userDto);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsersAll(Model model) {
        logger.info("Show all users");
        List<UserResponseDto> userResponseDtos = userService.findAll();
        model.addAttribute("users", userResponseDtos);

        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String showProfile(@PathVariable String id, Model model) {
        logger.info("Search for [{}] to show profile", id);
        UserResponseDto userResponseDto = userService.findUser(id);
        model.addAttribute("user", userResponseDto);

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable String id, Model model, HttpSession httpSession) {
        UserResponseDto sessionedUser = (UserResponseDto) httpSession.getAttribute("sessionedUser");
        if(isUnathorized(sessionedUser)) {
            logger.info("User not logged in tries access [{}]'s info", id);
            return "user/login";
        }
        checkAccessPermission(id, sessionedUser);

        logger.info("[{}] in updateForm for update info", id);
        model.addAttribute("user", sessionedUser);

        return "user/update_form";
    }

    @PutMapping("/users/{id}/update")
    public String updateInfo(@PathVariable String id, UserDto userDto, HttpSession httpSession) {
        UserResponseDto sessionedUser = (UserResponseDto) httpSession.getAttribute("sessionedUser");
        if(isUnathorized(sessionedUser)) {
            logger.info("User not logged in tries access {}'s info", id);
            return "user/login";
        }
        checkAccessPermission(id, sessionedUser);
        logger.info("[{}] updated info [{}]", id, userDto);
        userService.updateUserInfo(userDto);

        return "redirect:/users";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        logger.info("user in login_form");

        return "user/login";
    }

    @PostMapping("/user/login")
    public String loginUser(LoginDto loginDto, HttpSession httpSession) {
        logger.info("[{}] request login",loginDto.getUserId());
        UserResponseDto loginUser = userService.login(loginDto);

        if(loginUser == null) {
            return "user/login_failed";
        }

        httpSession.setAttribute("sessionedUser", loginUser);

        return "redirect:/qna/all";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession httpSession) {
        userService.logout(httpSession);

        return "redirect:/qna/all";
    }

    private boolean isUnathorized(UserResponseDto sessionedUser) {
        return sessionedUser == null;
    }

    // session정보와 pathID 확인
    private void checkAccessPermission(String id, UserResponseDto sessionedUser) {
        if(!sessionedUser.isSameId(id)){
            logger.info("[{}] tries access [{}]'s info", sessionedUser.getUserId(), id);
            throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

}
