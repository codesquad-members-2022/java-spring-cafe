package com.kakao.cafe.Controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserCreateDto;
import com.kakao.cafe.domain.dto.UserDto;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public String signup(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());

        return "user/form";
    }


    @PostMapping("/users")
    public String create(UserCreateDto userCreateDto) {
        User user = new User(userService.nextUserSequence(), userCreateDto);

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<UserDto> allUsers = userService.findUsers().stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());

        model.addAttribute("allUsers", allUsers);

        return "/user/list";
    }

    @GetMapping("users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        UserDto findUser = new UserDto(userService.findByUserId(userId));

        model.addAttribute("findUser", findUser);

        return "/user/profile";
    }
}
