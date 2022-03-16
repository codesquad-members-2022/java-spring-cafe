package com.kakao.cafe.config;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.UserSaveDto;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.UserService;
import org.springframework.stereotype.Component;

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

//    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            UserSaveDto userSaveDto = createRandomUser(i);
            try {
                userService.save(userSaveDto);
            } catch (Exception e) {
                continue;
            }
            if (random.nextBoolean()) {
                ArticleDto articleDto = createRandomArticleSaveDto(userSaveDto.getUserId());
                articleService.save(articleDto);
            }
        }
    }

    private UserSaveDto createRandomUser(int i) {
        UserSaveDto userSaveDto = new UserSaveDto();
        String[] emails = {"naver", "kakao", "gmail"};
        userSaveDto.setUserId("userId" + (random.nextInt(100) + 1));
        userSaveDto.setName("name" + i);
        userSaveDto.setPassword("password" + i);
        userSaveDto.setEmail("email" + (random.nextInt(100) + 1) + "@" + emails[random.nextInt(3)] + ".com");
        return userSaveDto;
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
