package kr.codesquad.cafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", service.findAll());

        return "users/list";
    }

    @PostMapping("/users")
    public String processCreationForm(UserCreationForm form, Model model) {
        try {
            User user = new User();
            user.setUserId(form.getUserId());
            user.setPassword(form.getPassword());
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            service.join(user);

            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("temp", form);

            return "users/form";
        }
    }

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable("userId") String userId, Model model) {
        try {
            model.addAttribute("user", service.findByUserId(userId));
            return "users/profile";
        } catch (NoSuchElementException e) {

            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        }
    }

    @GetMapping("/users/{userId}/form")
    public String viewUpdateForm(@PathVariable("userId") String userId, Model model) {
        try {
            model.addAttribute("user", service.findByUserId(userId));

            return "users/updateForm";
        } catch (NoSuchElementException e) {

            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        }
    }

    @PutMapping("/users/{userId}/update")
    public String processUpdateForm(@PathVariable("userId") String userId, UserUpdateForm form, Model model) {
        try {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(form.getNewPassword());
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            service.update(user, form.getOldPassword());

            //noinspection SpringMVCViewInspection
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("user", service.findByUserId(userId))
                    .addAttribute("message", e.getMessage());

            return "users/updateForm";
        }
    }
}
