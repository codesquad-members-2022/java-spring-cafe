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
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "jun@naver.com", "1234", "Jun"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "minsong@naver.com", "1234", "MinJe"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "pio@naver.com", "1234", "Pio"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "donggi@naver.com", "1234", "donggi"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "Nohri@naver.com", "1234", "Nohri"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "Vans@naver.com", "1234", "Vans"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "Phill@naver.com", "1234", "Phill"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "BC@naver.com", "1234", "BC"));
        memberRepository.insert(new Member(SequenceGenerator.getMemberSequence(), "Tany@naver.com", "1234", "Tany"));
        memberRepository.insert((new Member(SequenceGenerator.getMemberSequence(), "Hanse@naver.com", "1234", "Hanse")));
    }

    private void initArticleData() {
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분 참 쉽죠?", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "안녕하세요? JK입니다.", "저희 CS미션은 아래와 같이 만들어졌습니다.", "JK", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분, 크롱입니다.", "여러분 안녕하세요! 크롱입니다. 한 번 써봤습니다.", "크롱", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "회고 안 쓰면 다음 달 등록 못합니다.", "여러분 구현을 여기서 잘 하는 것은 큰 의미가 없어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "여러분, 영한님 너~무 사람 좋아요.", "영한님 스프링 입문편 결제해서 꼭 들어보세요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "가끔씩 모각코 해보세요!", "가끔 기분을 환기하는 것은 반드시 필요합니다. 산책 하세요.", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
        articleRepository.insert(new Article(SequenceGenerator.getArticleSequence(), "JK, Honux, 크롱, 자바지기가 함께 코드스쿼드를 만들었습니다.", "코드스쿼드 너무 좋습니다. 나단 잘생겼어요!", "Honux", LocalDateTime.now(), LocalDateTime.now().plusDays(3), getRandomNumber()));
    }

    private int getRandomNumber() {
        return random.nextInt(1500) + 1;
    }
}
