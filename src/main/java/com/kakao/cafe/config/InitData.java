package com.kakao.cafe.config;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class InitData {

    private final UserService userService;
    private final ArticleService articleService;
    private final Random random = new Random();

    public InitData(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            UserDto userDto = createRandomUser(i);
            try {
                userService.save(userDto);
            } catch (Exception e) {
                continue;
            }
            if (random.nextBoolean()) {
                ArticleDto articleDto = createRandomArticleSaveDto(userDto.getUserId());
                articleService.save(articleDto);
            }
        }
    }

    private UserDto createRandomUser(int i) {
        UserDto userDto = new UserDto();
        String[] emails = {"naver", "kakao", "gmail"};
        userDto.setUserId("userId" + (random.nextInt(100) + 1));
        userDto.setName("name" + i);
        userDto.setPassword("password" + i);
        userDto.setEmail("email" + (random.nextInt(100) + 1) + "@" + emails[random.nextInt(3)] + ".com");
        return userDto;
    }

    private ArticleDto createRandomArticleSaveDto(String userId) {
        String title = "title " + random.nextInt(1000) + 1;
        String contents = "";
        for (int j = 0; j < 5; j++) {
            contents += "content " + random.nextInt(1000) + 1 + "\r\n";
        }
        ArticleDto articleDto = new ArticleDto();
        articleDto.setUserId(userId);
        articleDto.setContents(contents);
        articleDto.setTitle(title);
        return articleDto;
    }
}
