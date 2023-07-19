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
     * Caffeine cache manager
     */
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     * Redis cache manager
     */
    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    /**
     * Home page recommendation
     */
    public static final String HOME_BOOK_CACHE_NAME = "homeBookCache";

    /**
     * Book Hit List Cache
     */
    public static final String BOOK_VISIT_RANK_CACHE_NAME = "bookVisitRankCache";

    /**
     * Book Newest Rank List Cache
     */
    public static final String BOOK_NEWEST_RANK_CACHE_NAME = "bookNewestRankCache";

    /**
     * Book Update Rank List Cache
     */
    public static final String BOOK_UPDATE_RANK_CACHE_NAME = "bookUpdateRankCache";

    /**
     * Book Category List Cache
     */
    public static final String BOOK_CATEGORY_LIST_CACHE_NAME = "bookCategoryListCache";

    /**
     * Book chapter cache
     */
    public static final String BOOK_CHAPTER_CACHE_NAME = "bookChapterCache";

    /**
     * Book information cache
     */
    public static final String BOOK_INFO_CACHE_NAME = "bookInfoCache";

    /**
     * Cache configuration constants
     */
    @Getter
    @AllArgsConstructor
    public enum CacheEnum {
        HOME_BOOK_CACHE(2, HOME_BOOK_CACHE_NAME, 60 * 60 * 24, 1000),

        BOOK_VISIT_RANK_CACHE(2, BOOK_VISIT_RANK_CACHE_NAME, 60 * 60 * 6, 10),

        BOOK_NEWEST_RANK_CACHE(0, BOOK_NEWEST_RANK_CACHE_NAME, 60 * 30, 1),

        BOOK_UPDATE_RANK_CACHE(0, BOOK_UPDATE_RANK_CACHE_NAME, 60, 1),

        BOOK_CATEGORY_LIST_CACHE(0, BOOK_CATEGORY_LIST_CACHE_NAME, 0, 2),

        BOOK_CHAPTER_CACHE(2, BOOK_CHAPTER_CACHE_NAME, 60 * 60 * 6, 10),

        BOOK_INFO_CACHE(0, BOOK_INFO_CACHE_NAME, 60 * 60 * 12, 1000),

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

        public boolean isLocal() {
            return type <= 1;
        }

        public boolean isRemote() {
            return type >= 1;
        }

    }
}
