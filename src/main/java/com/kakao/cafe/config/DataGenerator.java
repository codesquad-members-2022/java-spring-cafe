package com.kakao.cafe.config;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.SequenceGenerator;
import com.kakao.cafe.core.repository.article.ArticleRepository;
import com.kakao.cafe.core.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.Random;

public class DataGenerator {

    private static final Random random = new Random();
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public DataGenerator(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        init();
    }

    private void init() {
        initMemberData();
        initArticleData();
    }

    private void initMemberData() {
        memberRepository.insert(new Member(1, "jun@naver.com", "1234", "Jun"));
        memberRepository.insert(new Member(2, "minsong@naver.com", "1234", "MinJe"));
        memberRepository.insert(new Member(3, "pio@naver.com", "1234", "Pio"));
        memberRepository.insert(new Member(4, "donggi@naver.com", "1234", "donggi"));
        memberRepository.insert(new Member(5, "Nohri@naver.com", "1234", "Nohri"));
        memberRepository.insert(new Member(6, "Vans@naver.com", "1234", "Vans"));
        memberRepository.insert(new Member(7, "Phill@naver.com", "1234", "Phill"));
        memberRepository.insert(new Member(8, "BC@naver.com", "1234", "BC"));
        memberRepository.insert(new Member(9, "Tany@naver.com", "1234", "Tany"));
        memberRepository.insert((new Member(10, "Hanse@naver.com", "1234", "Hanse")));
    }

    private void initArticleData() {
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분 참 쉽죠?", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "안녕하세요? JK입니다.", "저희 CS미션은 아래와 같이 만들어졌습니다.", "JK", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분, 크롱입니다.", "여러분 안녕하세요! 크롱입니다. 한 번 써봤습니다.", "크롱", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "회고 안 쓰면 다음 달 등록 못합니다.", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분 참 쉽죠?", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분 참 쉽죠?", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분 참 쉽죠?", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), 3));
    }
}
