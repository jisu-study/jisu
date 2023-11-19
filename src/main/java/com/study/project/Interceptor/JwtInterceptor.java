package com.study.project.Interceptor;

import com.study.project.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String method = request.getMethod();
        log.debug("Intercept된 요청 Method:{}", method);
        if(method.equals("GET")) return true;

        final String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            log.debug("토큰이 없어요!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        try{
            String jwtToken = token.substring(7);
            Jws<Claims> parsedToken = JWTUtil.validateToken(jwtToken);
            return true;
        }catch(Exception e){
            log.debug("만료된 토큰이거나 유효하지 않는 토큰입니다, 에러내용 : {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
            return false;
        }
    }

}
