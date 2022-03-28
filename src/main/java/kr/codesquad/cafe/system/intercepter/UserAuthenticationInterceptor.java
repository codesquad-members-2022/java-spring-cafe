package kr.codesquad.cafe.system.intercepter;

import kr.codesquad.cafe.user.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UserAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariable = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String targetUserId = (String) pathVariable.get("userId");
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser.userIdIs(targetUserId)) {
            return true;
        }

        response.sendRedirect("/badRequest");

        return false;
    }
}
