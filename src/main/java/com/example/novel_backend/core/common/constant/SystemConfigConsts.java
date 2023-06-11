package com.example.novel_backend.core.common.constant;

/**
 * System configuration-related constants
 *
 *  @author :Contanstin
 * @create 2023/6/11 16:21
 */
public class SystemConfigConsts {
    /**
     * Constant class instantiation exception messages
     */
    public static final String CONST_INSTANCE_EXCEPTION_MSG = "Constant class";

    /**
     * Preventing classes from being instantiated
     */
    private SystemConfigConsts() {
        throw new IllegalStateException(CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * Http Request Authentication Header
     */
    public static final String HTTP_AUTH_HEADER_NAME = "Authorization";
}
