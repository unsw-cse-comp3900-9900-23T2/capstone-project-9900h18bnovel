package com.example.novel_backend.core.common.resp;

import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Objects;

/**
 * Http Rest response tool and data format encapsulation
 *
 * @author :Contanstin
 * @create 2023/6/11 22:06
 */
@Data
public class RestResp<T> {

    /**
     * Response Code
     */
    @Schema(description = "Error code, 00000 - no error")
    private String code;

    /**
     * Response messages
     */
    @Schema(description = "Response messages")
    private String message;

    /**
     * Response data
     */
    @Schema(description = "Response data")
    private T data;

    private RestResp(){
        this.code = ErrorCodeEnum.OK.getCode();
        this.message = ErrorCodeEnum.OK.getMessage();
    }

    private RestResp(ErrorCodeEnum errorCodeEnum){
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    private RestResp(T data){
        this();
        this.data = data;
    }

    /**
     * Business processed successfully, no data returned
     */
    public static RestResp<Void> ok(){
        return new RestResp<>();
    }

    /**
     * Business processed successfully with data returned
     */
    public static <T> RestResp<T> ok(T data){
        return new RestResp<>(data);
    }

    /**
     * Business processing failure
     */
    public static RestResp<Void> fail(ErrorCodeEnum errorCodeEnum){
        return new RestResp<>(errorCodeEnum);
    }

    /**
     * System error
     */
    public static RestResp<Void> error() {
        return new RestResp<>(ErrorCodeEnum.SYSTEM_ERROR);
    }
}
