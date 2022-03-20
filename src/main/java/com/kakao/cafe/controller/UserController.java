package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.common.AccessRestrictionException;
import com.kakao.cafe.exception.common.CommonException;
import com.kakao.cafe.exception.user.UserDomainException;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.session.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.kakao.cafe.message.UserDomainMessage.UPDATE_ACCESS_RESTRICTION_MESSAGE;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(NewUserParam newUserParam) {
        userService.add(newUserParam);
        return "redirect:/users";
    }

    @GetMapping
    public ModelAndView getUsers(ModelAndView mav) {
        mav.setViewName("user/list");
        mav.addObject("users", userService.searchAll());
        return mav;
    }

    @GetMapping("/{userId}")
    public ModelAndView getUserProfile(@PathVariable String userId, ModelAndView mav) {
        mav.setViewName("user/profile");
        mav.addObject("user", userService.search(userId));
        return mav;
    }

    @GetMapping("/{userId}/form")
    public ModelAndView goUpdateForm(@PathVariable String userId,
                                     @SessionAttribute("sessionUser") SessionUser sessionUser,
                                     ModelAndView mav) {

        mav.setViewName("user/updateForm");

        if (!sessionUser.ownerOf(userId)) {
            throw new AccessRestrictionException(HttpStatus.FORBIDDEN, "error/4xx", UPDATE_ACCESS_RESTRICTION_MESSAGE);
        }

        return mav;
    }

    @PutMapping("/{userId}")
    public String modifyProfile(ModifiedUserParam modifiedUserParam, HttpSession session) {
        User updateUser = userService.update(modifiedUserParam);
        SessionUser sessionUser = new SessionUser(updateUser);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/users";
    }

    @ExceptionHandler({UserDomainException.class})
    private ResponseEntity<String> except(UserDomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler({CommonException.class})
    private ModelAndView goErrorPage(CommonException ex) {
        ModelAndView mav = new ModelAndView(ex.getViewName());
        mav.setStatus(ex.getHttpStatus());
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
