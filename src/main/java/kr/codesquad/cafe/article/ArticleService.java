package kr.codesquad.cafe.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public void post(Article article) {
        repository.save(article);
    }

    public Article retrieve(long id) {
        return repository.findOne(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시물입니다."));
    }

    public List<Article> retrieveAll() {
        return repository.findAll();
    }
}
