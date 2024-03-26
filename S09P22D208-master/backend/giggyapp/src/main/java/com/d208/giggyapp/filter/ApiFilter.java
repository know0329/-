package com.d208.giggyapp.filter;

import com.d208.giggyapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiFilter implements Filter {
    private final RedisService redisService;

//    @Autowired
//    public ApiFilter(RedisService redisService) {
//        this.redisService = redisService;
//    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 리퀘스트의 헤더에서 토큰을 추출한다.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accessToken = httpRequest.getHeader("Authorization");
        String requestURI = httpRequest.getRequestURI();

        // 로그인, 회원가입은 토큰 검증 X
//        if (requestURI.equals("/api/v1/app/user/login") || requestURI.equals("/api/v1/app/user/signup")) {
//            System.out.println("No Certification");
//            chain.doFilter(request, response);
//        }

        // 레디스에 토큰이 존재하는지 확인한다.
        if (!redisService.getAccessToken(accessToken)) {
            tokenExpired(request, response, chain);
        } else // 다음 필터 또는 요청 핸들러로 제어를 넘김
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    // 토큰 만료  처리
    public void tokenExpired(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 현재 요청을 HttpServletRequest로 변환
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 새로운 요청을 생성 (토큰 만료)
        HttpServletRequestWrapper newRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getRequestURI() {
                return "/api/v1/expired"; // 새로운 요청의 URI 설정
            }
        };

        // 다음 필터 또는 요청 핸들러로 제어를 넘김
        chain.doFilter(newRequest, response);
    }
}
