package org.noahnyy.demo.spring.security.filter;

import com.sun.org.apache.xpath.internal.operations.Bool;

import org.noahnyy.demo.spring.security.config.JwtAudienceConfig;
import org.noahnyy.demo.spring.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2020/2/21
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtAudienceConfig audience;

    @Value("${web.excludePathPatterns}")
    private String[] excludePathPatterns;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            throw new RuntimeException("用户未登录，请先登录！");
        }
        // 获取token
        final String token = authHeader.substring(7);

        JwtTokenUtil.parseJWT(token, audience.getBase64Secret());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        for (String excludePathPattern : excludePathPatterns) {
            if (Objects.equals(requestURI, excludePathPattern)) {
                return Boolean.TRUE;
            }
        }
        return super.shouldNotFilter(request);
    }
}
