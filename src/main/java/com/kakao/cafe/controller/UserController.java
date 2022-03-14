package com.kakao.cafe.controller;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String findAllUser(Model model) {
        log.info("유저 리스트 조회 시도");
        List<UserResponseDto> users = userService.findUsers();
        model.addAttribute("users", users);
        log.info("유저 리스트 조회 성공 유저 수={}", users.size());
        log.debug("유저리스트={}", users);
        return "user/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        log.info("유저 회원가입 시도");
        UserResponseDto userResponseDto = userService.signUp(user);
        log.info("{} 이메일을 가지는 유저 회원가입 성공", userResponseDto.getEmail());
        log.debug("회원가입={}", userResponseDto);
        return "redirect:/users/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        log.info("유저 프로필 조회 시도");
        UserResponseDto findUserResponseDto = userService.findIdUser(userId);
        model.addAttribute("user", findUserResponseDto);
        log.info("{} 유저 프로필 조회 성공", findUserResponseDto.getUserId());
        log.debug("프로필조회={}", findUserResponseDto);
        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String userUpdateFrom(@PathVariable String userId, Model model) {
        log.info("유저 정보 변경 시도");
        UserResponseDto findUser = userService.findIdUser(userId);
        model.addAttribute("user", findUser);
        log.debug("변경 전 유저 정보={}", findUser);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String userUpdate(@PathVariable String userId, @ModelAttribute UserRequestDto userUpdateInfo) {
        UserResponseDto updateUser = userService.update(userId, userUpdateInfo);
        log.debug("변경 후 유저 정보={}", updateUser);
        log.info("유저 정보 변경 성공");
        return "redirect:/users/list";
    }
}
