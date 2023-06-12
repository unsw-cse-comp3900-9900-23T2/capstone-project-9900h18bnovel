package com.example.novel_backend.core.common.exception;

import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom business exceptions to be thrown in case of business errors when handling user requests
 *
 * @author :Contanstin
 * @create 2023/6/12 20:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private final ErrorCodeEnum errorCodeEnum;

    public BusinessException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getMessage(), null, false, false);
        this.errorCodeEnum = errorCodeEnum;
    }
}
