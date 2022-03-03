package com.kakao.cafe.integration;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    private static final String WRITER = "writer";
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArticleSetUp articleSetUp;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article(WRITER, TITLE, CONTENTS);
    }

    @AfterEach
    public void exit() {
        articleSetUp.rollback();
    }

    @Test
    @DisplayName("글을 작성하는 화면을 보여준다")
    public void createQuestionTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(get("/questions")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(view().name("qna/form"));
    }

    @Test
    @DisplayName("글을 작성하고 업로드한다")
    public void createTest() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(post("/questions")
            .param("writer", WRITER)
            .param("title", TITLE)
            .param("contents", CONTENTS)
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().is3xxRedirection())
            .andExpect(model().attribute("article", allOf(
                hasProperty("writer", is(WRITER)),
                hasProperty("title", is(TITLE))
            )))
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("등록된 모든 글을 화면에 출력한다")
    public void listArticlesTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = mockMvc.perform(get("/")
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("articles", List.of(savedArticle)))
            .andExpect(view().name("qna/list"));
    }

    @Test
    @DisplayName("질문 id 로 선택한 질문을 화면에 출력한다")
    public void showArticleTest() throws Exception {
        // given
        Article savedArticle = articleSetUp.saveArticle(article);

        // when
        ResultActions actions = mockMvc.perform(get("/articles/" + savedArticle.getArticleId())
            .accept(MediaType.parseMediaType("application/html;charset=UTF-8")));

        // then
        actions.andExpect(status().isOk())
            .andExpect(model().attribute("article", savedArticle))
            .andExpect(view().name("qna/show"));
    }

}
