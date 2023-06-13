package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Image Verification Code Response DTO
 *
 * @author :Contanstin
 * @create 2023/6/13 14:20
 */
@Data
@Builder
public class ImgVerifyCodeRespDto {

    /**
     * The current session ID,
     * which identifies the session to which the graphic verification code is to be changed
     * */
    @Schema(description = "sessionId")
    private String sessionId;

    /**
     * Base64 编码的验证码图片
     * */
    @Schema(description = "Base64 captcha images")
    private String img;
}
