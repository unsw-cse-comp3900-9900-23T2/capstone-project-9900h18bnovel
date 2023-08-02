package com.example.novel_backend.core.interceptor;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author :Contanstin
 * @create 2023/8/2 13:08
 */
@Component
@ConditionalOnProperty(value = "novel.xss.enabled", havingValue = "true")
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
@EnableConfigurationProperties(value = {XssProperties.class})
@RequiredArgsConstructor
public class XssFilter implements Filter {

    private final XssProperties xssProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (handleExcludeUrl(req)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) servletRequest);
        filterChain.doFilter(xssRequest, servletResponse);
    }


    private boolean handleExcludeUrl(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(xssProperties.excludes())) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : xssProperties.excludes()) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}
