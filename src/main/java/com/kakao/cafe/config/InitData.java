package com.kakao.cafe.config;

import com.kakao.cafe.controller.dto.ArticleSaveDto;
import com.kakao.cafe.domain.User;
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
            User user = createRandomUser(i);
            try {
                userService.join(user);
            } catch (Exception e) {
                continue;
            }
            if (random.nextBoolean()) {
                ArticleSaveDto articleSaveDto = createRandomArticleSaveDto(user);
                articleService.save(articleSaveDto);
            }
        }
    }

    private User createRandomUser(int i) {
        String[] emails = {"naver", "kakao", "gmail"};
        String userId = "userId" + (random.nextInt(100) + 1);
        String name = "name" + i;
        String password = "password" + i;
        String email = "email" + (random.nextInt(100) + 1) + "@" + emails[random.nextInt(3)] + ".com";
        return new User(userId, password, name, email);
    }

    private ArticleSaveDto createRandomArticleSaveDto(User user) {
        String title = "title " + random.nextInt(1000) + 1;
        String contents = "";
        for (int j = 0; j < 5; j++) {
            contents += "content " + random.nextInt(1000) + 1 + "\r\n";
        }
        ArticleSaveDto articleSaveDto = new ArticleSaveDto();
        articleSaveDto.setUserId(user.getUserId());
        articleSaveDto.setContents(contents);
        articleSaveDto.setTitle(title);
        return articleSaveDto;
    }
}
