package com.example.interceptor;

import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("=== 进入 JWT 拦截器 ===");

        String auth = request.getHeader("Authorization");

        System.out.println("Authorization = " + auth);

        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        String token = auth.substring(7);

        Integer userId = JwtUtil.parseToken(token);

        System.out.println("解析出来的 userId = " + userId);

        request.setAttribute("userId", userId);

        return true;
    }

}
