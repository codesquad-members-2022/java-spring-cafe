package com.kakao.cafe.qna.domain;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	// 요청 받은 데이터 검증은 Controller or Service?
	// Controller는 접근자 검증
	// 저장할 값에 대한 검증은 전달해줄 레이어에서 하는게 좋을 것 같다.
	public void write(ArticleDto articleDto) {
		if (articleDto.isOneMoreBlank()) {
			throw new IllegalArgumentException(articleDto.getErrorMessageWithBlank());
		}
		Article question = new Article(articleDto.getWriter(), articleDto.getTitle(), articleDto.getContents());
		articleRepository.save(question);
	}
}

