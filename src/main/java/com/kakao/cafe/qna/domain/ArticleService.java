package com.kakao.cafe.qna.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserService;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserService userService;  // 서비스레이어가 나을까요? UserRepository 가 나을까요? 기준은 뭘까요?

	public ArticleService(ArticleRepository articleRepository, UserService userService) {
		this.articleRepository = articleRepository;
		this.userService = userService;
	}

	public long write(ArticleDto.WriteRequest writeRequest) {
		User user = userService.getUserByUserId(writeRequest.getUserId());
		Article question = new Article(user.getName(), writeRequest.getTitle(), writeRequest.getContents(), user.getId());
		Article getArticle = articleRepository.save(question);
		return getArticle.getId();
	}

	public List<ArticleDto.WriteResponse> getAllArticles() {
		List<Article> articles = articleRepository.findAll();
		return articles.stream()
			.map(article -> new ArticleDto.WriteResponse(article))
			.collect(toUnmodifiableList());
	}

	public ArticleDto.WriteResponse read(Long id) {
		String errors = String.format("Article %d", id);
		Article article = articleRepository.findById(id)
			.orElseThrow(() -> {
				throw new DomainNotFoundException(errors);
			});
		return new ArticleDto.WriteResponse(article);
	}
}

