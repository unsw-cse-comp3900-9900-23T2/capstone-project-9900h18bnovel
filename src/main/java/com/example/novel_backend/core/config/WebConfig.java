package com.example.novel_backend.core.config;

import com.example.novel_backend.core.interceptor.FlowLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  Spring Web Mvc related configurations should not include the @EnableWebMvc annotation,
 *  as this will cause jackson's global configuration to fail.
 *  This is because the @EnableWebMvc annotation will cause the
 *  WebMvcAutoConfiguration will be invalidated by the @EnableWebMvc annotation.
 *
 * @author :Contanstin
 * @create 2023/8/2 13:53
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FlowLimitInterceptor flowLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 流量限制拦截器
        registry.addInterceptor(flowLimitInterceptor)
                .addPathPatterns("/**")
                .order(0);

    }
}
