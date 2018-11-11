package com.kaka.house.web.interceptor;

import com.google.common.base.Joiner;
import com.kaka.house.common.constants.CommonConstants;
import com.kaka.house.common.model.User;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.Kernel;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    //拦截器执行前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry entry:map.entrySet()) {
            map.forEach((k,v)->{
                if (k.equals("errorMsg")||k.equals("successMsg")||k.equals("target")){
                    request.setAttribute(k, Joiner.on(",").join(v));
                }
            });
        }
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/static")||requestURI.startsWith("/error")){
            return true;
        }
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if (user != null){
            UserContext.setUser(user);
        }
        return true;
    }

    //拦截器执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //页面渲染后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
