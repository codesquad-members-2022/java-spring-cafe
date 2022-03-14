package kr.codesquad.cafe.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String viewQuestions(Model model) {
        model.addAttribute("articles", service.retrieveAll());

        return "index";
    }

    @PostMapping("/questions")
    public String processCreationForm(ArticleCreationForm form) {
        Article article = new Article();
        article.setWriter(form.getWriter());
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        service.post(article);

        return "redirect:/";
    }

}
