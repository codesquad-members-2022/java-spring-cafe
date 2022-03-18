package kr.codesquad.cafe.system.intercepter;

import kr.codesquad.cafe.article.Article;
import kr.codesquad.cafe.user.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriterAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Article article = (Article) modelAndView.getModelMap().getAttribute("article");
        String writerUserId = article.getWriterUserId();
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (!currentUser.userIdIs(writerUserId)) {
            response.sendRedirect("/badRequest");
        }
    }
}
