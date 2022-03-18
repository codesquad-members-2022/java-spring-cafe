package kr.codesquad.cafe.article;

import kr.codesquad.cafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
    public String processCreationForm(ArticleCreationForm form, HttpSession session) {
        Article article = new Article();
        User writer = (User) session.getAttribute("currentUser");
        article.setWriterUserId(writer.getUserId());
        article.setWriterName(writer.getName());
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        service.post(article);

        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String viewQuestion(@PathVariable("id") long id, Model model) {
        model.addAttribute("article", service.retrieve(id));

        return "qna/show";
    }

}
