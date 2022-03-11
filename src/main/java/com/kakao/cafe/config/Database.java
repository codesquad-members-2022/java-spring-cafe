package com.kakao.cafe.config;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private final List<Article> articles;
    private final List<Member> members;

    private final List<Integer> articlesId;
    private final List<Integer> membersId;
    private Map<String, List<?>> database = new ConcurrentHashMap<>();

    public Database() {
        this.articles = new ArrayList<>();
        this.articlesId = new ArrayList<>();
        this.members = new ArrayList<>();
        this.membersId = new ArrayList<>();

        database.put("ArticleId", this.articlesId);
        database.put("Article", this.articles);
        database.put("MemberId", this.membersId);
        database.put("Member", this.members);
    }

    public List<?> getIdDatabase(String type) {
        return database.get(type);
    }

    public List<?> getDatabase(String type) {
        return database.get(type);
    }

    public <T> T save(String type, T entity) {
        if (type.equals("Article")) {
            articles.add((Article) entity);
        } else {
            members.add((Member) entity);
        }
        return entity;
    }

    public List<Article> getArticles() {
        return new ArrayList<>(articles);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }
}
