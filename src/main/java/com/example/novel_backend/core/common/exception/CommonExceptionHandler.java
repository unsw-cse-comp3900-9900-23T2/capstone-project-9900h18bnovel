package com.example.novel_backend.core.common.exception;

import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.RestResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Generic exception handler
 *
 * @author :Contanstin
 * @create 2023/6/13 15:33
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * Handling data validation exceptions
     */
    @ExceptionHandler(BindException.class)
    public RestResp<Void> handlerBindException(BindException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
    }

    /**
     * Handling business exceptions
     */
    @ExceptionHandler(BusinessException.class)
    public RestResp<Void> handlerBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(e.getErrorCodeEnum());
    }

    /**
     * Handling system exceptions
     */
    @ExceptionHandler(Exception.class)
    public RestResp<Void> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResp.error();
    }

}
