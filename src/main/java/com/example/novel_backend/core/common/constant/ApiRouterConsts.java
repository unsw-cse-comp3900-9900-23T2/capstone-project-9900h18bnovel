package com.example.novel_backend.core.common.constant;

/**
 * API Routing Constants
 *
 * @author :Contanstin
 * @create 2023/6/11 16:25
 */
public class ApiRouterConsts {

    private ApiRouterConsts() {
        throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * API Request Path Prefix
     */
    public static final String API_URL_PREFIX = "/api";

    /**
     * Front-end portal system request path prefix
     */
    public static final String API_FRONT_URL_PREFIX = API_URL_PREFIX + "/front";

    /**
     * User module request path prefix
     */
    public static final String USER_URL_PREFIX = "/user";


    /**
     * Front-end portal system User module request path prefix
     */
    public static final String API_FRONT_USER_URL_PREFIX = API_FRONT_URL_PREFIX + USER_URL_PREFIX;
}
