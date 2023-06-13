package com.example.novel_backend.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Cache-related constants
 *
 * @author :Contanstin
 * @create 2023/6/12 14:12
 */
public class CacheConsts {

    /**
     * This project Redis cache prefix
     */
    public static final String REDIS_CACHE_PREFIX = "Cache::Novel::";

    /**
     * Mailbox Captcha Cache KEY
     */
    public static final String EMAIL_VERIFY_CODE_CACHE_KEY =
            REDIS_CACHE_PREFIX + "emailVerifyCodeCache::";

    /**
     * Image captcha cache KEY
     */
    public static final String IMG_VERIFY_CODE_CACHE_KEY =
            REDIS_CACHE_PREFIX + "imgVerifyCodeCache::";


    /**
     * Cache configuration constants
     */
    @Getter
    @AllArgsConstructor
    public enum CacheEnum {
        USER_INFO_CACHE(2, EMAIL_VERIFY_CODE_CACHE_KEY, 60 * 60 * 24, 10000);
        /**
         * Cache type 0 - local 1 - local and remote 2 - remote
         */
        private int type;
        /**
         * Name of the cache
         */
        private String name;
        /**
         * Expiry time (sec) 0 - never expires
         */
        private int ttl;
        /**
         * Maximum capacity
         */
        private int maxSize;
    }
}
