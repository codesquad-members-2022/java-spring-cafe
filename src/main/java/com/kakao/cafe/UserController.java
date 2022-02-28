package com.kakao.cafe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getUsers() {
        return "user/list";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "user/form";
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam String userId,
        @RequestParam String password,
        @RequestParam String name,
        @RequestParam String email) {

        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/users";
    }

}
