package ru.lexbatalin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long time = System.currentTimeMillis();
        request.setAttribute("time", time);
        LOG.info("Pre handle method, time in ms is: " + time + " ms.");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long totalTime = System.currentTimeMillis() - (Long) request.getAttribute("time");
        modelAndView.addObject("totalTime", totalTime);
        LOG.info("Post handle method, total time passed: " + totalTime + " ms.");
    }

}
