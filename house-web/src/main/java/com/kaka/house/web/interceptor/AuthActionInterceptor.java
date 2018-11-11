package com.kaka.house.web.interceptor;

import com.kaka.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Component
public class AuthActionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = UserContext.getUser();
        if (user == null){
            String msg = URLEncoder.encode("请先登录", "utf8");
            String target = URLEncoder.encode(request.getRequestURL().toString(), "utf8");
            if ("get".equalsIgnoreCase(request.getMethod())){
                response.sendRedirect("/accounts/signin?errorMsg="+msg+"&target="+target);
            }else {
                response.sendRedirect("/accounts/signin?erroeMsg="+msg);
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
