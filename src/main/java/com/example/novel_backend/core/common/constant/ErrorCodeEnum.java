package com.example.novel_backend.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error code enumeration class.
 * <p>
 * Error codes are strings of 5 bits, divided into two parts: the source of the error + a four-digit number.
 * The source of the error is classified as A/B/C. A indicates that the error originates from the user,
 * e.g. a parameter error, a low version of the user's installation, a user payment timeout, etc.; B
 * C indicates that the error originates from a third-party service, such as a CDN service error, message delivery timeout,
 * etc. The four digits are numbered from 0001 to 9999, with 100 steps between the major categories.
 * Step spacing of 100 is reserved.
 * <p>
 * Error codes are categorized into Level 1 macro error codes, Level 2 macro error codes, and Level 3 macro error codes.
 * In error scenarios where it is not possible to be more specific, the first level macro error code can be used directly.
 *
 * @author :Contanstin
 * @create 2023/6/11 16:08
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**
     * Return after correct execution
     */
    OK("00000", "Everything is ok"),

    /**
     * Level 1 macro error code, user-side error
     */
    USER_ERROR("A0001", "user-side error"),

    /**
     * Level 2 macro error code, user registration error
     */
    USER_REGISTER_ERROR("A0100", "user registration error"),

    /**
     * User authentication code error
     */
    USER_VERIFY_CODE_ERROR("A0240", "User authentication code error"),

    /**
     * Email already exists
     */
    EMAIL_EXIST("A0111", "Email already exists"),

    /**
     * User account does not exist
     */
    USER_ACCOUNT_NOT_EXIST("A0201", "User account does not exist"),

    /**
     * User password error
     */
    USER_PASSWORD_ERROR("A0210", "User password error"),

    /**
     * User photo error
     */
    USER_PHOTO_ERROR("A0220", "User photo error"),

    /**
     * Level 1 macro error code, system execution error
     */
    SYSTEM_ERROR("B0001", "System execution error"),

    /**
     * Level 2 macro error code, user request parameter error
     */
    USER_REQUEST_PARAM_ERROR("A0400", "User request parameter error"),

    /**
     * User comment fail
     */
    USER_COMMENTED("A2001", "You have already commented!");

    /**
     * Error Code
     */
    private final String code;

    /**
     * Error message
     */
    private final String message;



}
