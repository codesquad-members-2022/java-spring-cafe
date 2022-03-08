package kr.codesquad.cafe.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping ("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", service.findAll());

        return "/users/list";
    }

    @PostMapping("/users")
    public String processCreationForm(User user, Model model) {
        try {
            service.join(user);

            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());

            return "/users/form";
        }
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", service.findByUserId(userId).orElseThrow());

        return "/users/profile";
    }
}
