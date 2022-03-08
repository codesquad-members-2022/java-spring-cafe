package kr.codesquad.cafe.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

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

        return "users/list";
    }

    @PostMapping("/users")
    public String processCreationForm(User user, Model model) {
        try {
            service.join(user);

            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());

            return "/users/form";
        }
    }

    @GetMapping("/users/{id:[0-9]+}")
    public String viewUserProfile(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("user", service.findById(id));
            return "/users/profile";
        } catch (NoSuchElementException e) {
            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        }
    }
}
