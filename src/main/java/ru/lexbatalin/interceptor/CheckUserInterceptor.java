package ru.lexbatalin.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.lexbatalin.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

public class CheckUserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getRequestURI().contains("check_user")) {
            User user = (User) modelAndView.getModel().get("user");
            if (isNull(user) || !user.getIsAdmin()) {
                response.sendRedirect(request.getContextPath() + "/failed");
            }
        }
    }
}
