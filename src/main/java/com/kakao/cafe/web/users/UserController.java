package com.kakao.cafe.web.users;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final MemoryUserRepository repository;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(MemoryUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = repository.findAll();
        log.info("users list = {}", users);
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/create")
    public String addForm() {
        log.info("create form 접근");
        return "/user/form";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user) {
        repository.save(user);
        log.info("save user = {}", user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model) {
        User findUser = repository.findById(userId);
        log.info("user profile = {}", findUser);
        model.addAttribute("user", findUser);
        return "/user/profile";
    }
}
