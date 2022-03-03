package com.kakao.cafe.controller;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.users.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UserController {
    private final UserService userService;

    //@NoArgsConstructor : 롬복도 일종의 DI인가?
    //의존 주입을 받을 수 있는 방법 중 생성자를 통한 방법을 마련해줌.?
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/signup")
    public String signup(){
        return "user/form";
    }

//    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    @GetMapping(path ="users/signup2")
    public String save(User user) {
        userService.save(user);

        System.out.println(user.getUserId());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        System.out.println();
        //[Q] 왜 list로 써야 하는지 모르겠다. /user를 기본으로 인식하고 있네
        return "redirect:list";
    }

    @GetMapping("/users/list")
    public String list(){
        return "user/list";
    }
}
