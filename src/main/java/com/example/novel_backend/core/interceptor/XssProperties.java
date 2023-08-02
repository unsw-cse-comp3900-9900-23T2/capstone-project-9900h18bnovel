package com.example.novel_backend.core.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Xss configuration properties
 *
 * @author :Contanstin
 * @create 2023/8/2 13:22
 */
@ConfigurationProperties(prefix = "novel.xss")
public record XssProperties(Boolean enabled, List<String> excludes) {

}
