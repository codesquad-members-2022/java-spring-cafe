package com.kakao.cafe.web.users;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.web.SessionConst;
import com.kakao.cafe.web.validation.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidation userValidation;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidation);
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findUsers();
        log.info("users list = {}", users);
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/create")
    public String getUserForm(Model model) {
        log.info("create form 접근");
        model.addAttribute("user", new User());
        return "/user/form";
    }

    @PostMapping("/create")
    public String saveUser(@Validated @ModelAttribute User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/form";
        }

        userService.register(user);
        log.info("save user = {}", user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        User findUser = userService.findUserById(userId);
        log.info("user profile = {}", findUser);
        model.addAttribute("user", findUser);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUserProfileForm(@PathVariable String userId, Model model) {
        User findUser = userService.findUserById(userId);
        log.info("get profile update form");
        model.addAttribute("user", findUser);
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUserProfileForm(@Validated @ModelAttribute User user, BindingResult bindingResult, HttpServletResponse response, HttpSession session) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.info("업데이트 메서드 : 사용자 인자 오류");
            return "/user/updateForm";
        }

        Object value = session.getAttribute(SessionConst.SESSIONED_USER); // 로그인한 사용자 만 수정 가능
//        if(value == null) { // 세션이 없다면 예외처리
//            throw new AuthenticationException("로그인 하지 않은 사용자 입니다.");
//        }

        User sessionUser = (User) value; // 세션이 있다면 User로 캐스팅

        if (!sessionUser.isSameUserId(user)) { // 세션유저 정보를 통해 사용자로부터 받은 user 정보와 비교
            throw new AuthenticationException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        // 세션에 저장된 회원과 동일한 유저의 정보일때만
        boolean isSamePassword = userService.userUpdate(user);

        if (!isSamePassword) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            bindingResult.rejectValue("password", "notmatched", "비밀번호가 일치하지 않습니다.");
            log.info("업데이트 메서드 : 사용자 업데이트 실패");
            return "/user/updateForm";
        }

        return "redirect:/users";
    }
}
