package com.example.novel_backend.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Cross-domain configuration properties
 *
 * @author :Contanstin
 * @create 2023/6/23 16:01
 */
@ConfigurationProperties(prefix = "novel.cors")
@Data
public class CorsProperties {
    /**
     * 允许跨域的域名
     * */
    private List<String> allowOrigins;
}
