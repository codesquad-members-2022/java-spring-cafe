package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.qna.domain.Article.*;
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
		Article question = ArticleFactory.create(user.getName(), writeRequest.getTitle(), writeRequest.getContents(), user.getId());
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

	public ArticleDto.WriteResponse read(Long id) {
		return new ArticleDto.WriteResponse(get(id));
	}

	public ArticleDto.WriteResponse getArticle(Long id, Object sessionUser) {
		Article article = get(id);
		isValidWriter(article, (SessionUser)sessionUser);
		return new ArticleDto.WriteResponse(article);
	}

	private void isValidWriter(Article article, SessionUser sessionUser) {
		User user = getUserByUserId(sessionUser.getUserId());
		if (!user.isWriter(article)) {
			throw new IllegalArgumentException("invalid access to article board");
		}
	}

	public void edit(ArticleDto.EditRequest updateDto, Object sessionUser) {
		Article article = get(updateDto.getIdByLong());
		isValidWriter(article, (SessionUser)sessionUser);
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

	public void remove(Long id, Object sessionUser) {
		Article article = get(id);
		isValidWriter(article, (SessionUser)sessionUser);
		articleRepository.delete(id);
	}
}

