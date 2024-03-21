package com.example.rbac_back.Utils.Token;

import com.example.rbac_back.Utils.Token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthToken authTokenClass = handlerMethod.getBeanType().getAnnotation(AuthToken.class);
            AuthToken authTokenMethod = handlerMethod.getMethod().getAnnotation(AuthToken.class);

            // 如果方法或类上标记了注解
            if (authTokenMethod != null || authTokenClass != null) {
                // 从请求中获取Access Token
                String accessToken = request.getHeader("Authorization");

                // 验证Access Token
                if (accessToken != null && redisTemplate.hasKey(accessToken.substring(7))) {
                    return true;
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
        }
        return true;
    }
}
