package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.User;
import com.ttasjwi.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = new UserRepository();
    }

    @GetMapping("/new")
    public String createForm() {
        return "/users/createUserForm";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute User user) {
        userRepository.save(user);
        log.info("new user={}", user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        List<User> list = userRepository.findAll();
        model.addAttribute("users", list);
        return "/users/userList";
    }
}
