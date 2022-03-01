package com.kakao.cafe;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/profile";
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
        userRepository.save(user);

        // TODO: redirect 시 메서드 등록한 user 에 대한 테스트를 어떻게 작성해야할까?
        return "redirect:/users";
    }

}
