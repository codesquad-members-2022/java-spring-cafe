package kr.codesquad.cafe.article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private final List<Article> repository = Collections.synchronizedList(new ArrayList<>());

    public void save(Article article) {
        article.setId(repository.size() + 1);
        repository.add(article);
    }

    public Optional<Article> findOne(int index) {
        return Optional.ofNullable(repository.get(index - 1));
    }

    public List<Article> findAll() {
        return new ArrayList<>(repository);
    }
}
