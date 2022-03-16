package kr.codesquad.cafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "users/login";
    }

    @PostMapping("/login")
    public String processLogin(String userId, String password, HttpSession session) {
        try {
            userService.login(userId, password, session);

            return "redirect:/";
        } catch (Exception e) {
            return "users/login_failed";
        }
    }

    @PostMapping("/logout")
    public String processLogout(HttpSession session) {
        userService.logout(session);

        return "redirect:/";
    }
}
