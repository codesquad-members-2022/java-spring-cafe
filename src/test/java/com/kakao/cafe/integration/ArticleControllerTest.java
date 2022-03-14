package com.kakao.cafe.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.repository.DomainRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Deprecated
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    final static int EXISTING_ARTICLES_COUNT = 4;

    @Autowired
    MockMvc mvc;

    @Autowired
    DomainRepository<Article, Long> repository;

    static List<Article> articles = new Vector<>();

//    @BeforeAll
    static void init() {
        for (int i = 0; i < EXISTING_ARTICLES_COUNT; ++i) {
            articles.add(new Article(0, "writer", "title", "contents", LocalDate.now()));
        }
    }

//    @BeforeEach
    void setUp() {
//        repository.clear();
        articles.forEach(repository::save);
    }

    @DisplayName("모든 사용자가 게시글 작성 요청하면 게시글 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4writeArticle")
    void writeArticle(NewArticleParam newArticleParam) throws Exception {
        mvc.perform(post("/articles/write").params(convertToMultiValueMap(newArticleParam)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );
    }
    static Stream<Arguments> params4writeArticle() {
        return Stream.of(
                Arguments.of(new NewArticleParam("writer", "title", "contents")),
                Arguments.of(new NewArticleParam("writer", "title", "contents")),
                Arguments.of(new NewArticleParam("writer", "title", "contents")),
                Arguments.of(new NewArticleParam("writer", "title", "contents"))
                );
    }
    private MultiValueMap<String, String> convertToMultiValueMap(Object obj) {
        Map<String, String> map = new ObjectMapper().convertValue(obj, new TypeReference<>() {});
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(map);

        return params;
    }

    @DisplayName("메인 페이지를 요청하면 질문글 목록을 출력한다.")
    @Test
    void getArticles() throws Exception {
        mvc.perform(get("/"))
                .andExpectAll(
                        model().attributeExists("articles"),
//                        model().attribute("articles", articles),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );
    }

    @DisplayName("게시글 목록의 제목을 클릭하면 게시글 상세 페이지에 접속한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @ValueSource(ints = { 1, 2, 3, 4 })
    void getDetail(int id) throws Exception {
        mvc.perform(get("/articles/" + id))
                .andExpectAll(
                        model().attributeExists("article"),
//                        model().attribute("article", articles.get(id - 1)),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );
    }
}
