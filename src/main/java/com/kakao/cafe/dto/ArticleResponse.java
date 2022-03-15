package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.util.Mapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleResponse {

    private Integer articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    // relation
    private List<ReplyResponse> replies;
    private Integer replyCount;

    private ArticleResponse() {
    }

    public ArticleResponse(Integer articleId, String writer, String title, String contents,
        LocalDateTime createdDate) {
        this(articleId, writer, title, contents, createdDate, null, null);
    }


    public ArticleResponse(Integer articleId, String writer, String title, String contents,
        LocalDateTime createdDate, List<ReplyResponse> replies, Integer replyCount) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;

        this.replies = replies;
        this.replyCount = replyCount;
    }

    public static ArticleResponse of(Article article, List<Reply> replies) {
        List<ReplyResponse> responses = replies.stream()
            .map(reply -> Mapper.map(reply, ReplyResponse.class))
            .collect(Collectors.toList());

        return new ArticleResponse(
            article.getArticleId(),
            article.getWriter(),
            article.getTitle(),
            article.getContents(),
            article.getCreatedDate(),
            responses,
            responses.size()
        );
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArticleResponse article = (ArticleResponse) o;

        return articleId.equals(article.articleId)
            && writer.equals(article.writer)
            && title.equals(article.title)
            && contents.equals(article.contents);
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
            "articleId=" + articleId +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", createdDate=" + createdDate +
            ", replies=" + replies +
            ", replyCount=" + replyCount +
            '}';
    }
}
