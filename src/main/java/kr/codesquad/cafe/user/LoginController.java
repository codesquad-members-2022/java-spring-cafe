package kr.codesquad.cafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final String CURRENT_USER = "currentUser";
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
            User user = userService.findByUserId(userId);
            userService.validatePassword(user, password);
            session.setAttribute(CURRENT_USER, user);

            String destination = getDestination(session);
            return "redirect:" + destination;
        } catch (Exception e) {
            return "users/login_failed";
        }
    }

    private String getDestination(HttpSession session) {
        String destination = (String) session.getAttribute("destinationAfterLogin");

        if (destination == null) {
            return "/";
        }

        session.removeAttribute("destinationAfterLogin");

        return destination;
    }

    @PostMapping("/logout")
    public String processLogout(HttpSession session) {
        session.removeAttribute(CURRENT_USER);

        return "redirect:/";
    }
}
