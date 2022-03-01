package com.kakao.cafe.Controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserCreateDto;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String create(UserCreateDto userCreateDto) {
        User user = new User(userService.nextUserSequence(), userCreateDto);

        User joinUser = userService.join(user);

        return "redirect:/users";
    }
}
