package com.kakao.cafe.Controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.Controller.dto.UserRequestDto;
import com.kakao.cafe.Controller.dto.UserDto;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("userRequestDto", new UserRequestDto());

        return "user/form";
    }


    @PostMapping("/users")
    public String create(UserRequestDto userRequestDto) {
        User user = new User(userService.nextUserSequence(), userRequestDto);

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<UserDto> allUsers = userService.findUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        model.addAttribute("allUsers", allUsers);

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        UserDto findUser = new UserDto(userService.findByUserId(userId));

        model.addAttribute("findUser", findUser);

        return "/user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User findUser = userService.findByUserId(userId);
        UserRequestDto userRequestDto = new UserRequestDto(userId, null, findUser.getName(), findUser.getEmail());

        model.addAttribute("userRequestDto", userRequestDto);

        return "user/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, UserRequestDto userRequestDto) {
        User findUser = userService.findByUserId(userId);

        if (findUser.isCorrectPassword(userRequestDto.getPassword())) {
            findUser.update(userRequestDto.getPassword(), userRequestDto.getName(), userRequestDto.getEmail());
            userService.save(findUser);
        }

        return "redirect:/users";
    }
}
