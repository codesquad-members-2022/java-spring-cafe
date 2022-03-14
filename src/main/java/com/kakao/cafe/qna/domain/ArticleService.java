package com.kakao.cafe.qna.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.common.utils.session.SessionUser;
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
		User user = getUserByUserId(writeRequest.getUserId());
		Article question = new Article(user.getName(), writeRequest.getTitle(), writeRequest.getContents(), user.getId());
		Article getArticle = articleRepository.save(question);
		return getArticle.getArticleId();
	}

	private User getUserByUserId(String userId) {
		return userService.getUserByUserId(userId);
	}

	public List<ArticleDto.WriteResponse> getAllArticles() {
		List<Article> articles = articleRepository.findAll();
		return articles.stream()
			.map(article -> new ArticleDto.WriteResponse(article))
			.collect(toUnmodifiableList());
	}

	public ArticleDto.WriteResponse read(Long id, SessionUser sessionUser) {
		User user = getUserByUserId(sessionUser.getUserId());
		Article article = get(id);
		if (!user.isWriter(article)) {
			throw new IllegalArgumentException("invalid access to article board");
		}
		return new ArticleDto.WriteResponse(article);
	}

	public void edit(ArticleDto.EditRequest updateDto) {
		Article article = get(updateDto.getIdByLong());
		if (!userService.isExistByUserId(updateDto.getUserId())) {
			String errorMessage = String.format("no exist user: {}, invalid access to article", updateDto.getUserId());
			throw new IllegalArgumentException(errorMessage);
		}
		article.update(updateDto);
		articleRepository.save(article);
	}

	private Article get(Long id) {
		String errorMessage = String.format("no exist article : %s", id);
		return articleRepository.findById(id)
			.orElseThrow(() -> new DomainNotFoundException(errorMessage));
	}

	public void remove(Long id) {
		articleRepository.delete(id);
	}
}

