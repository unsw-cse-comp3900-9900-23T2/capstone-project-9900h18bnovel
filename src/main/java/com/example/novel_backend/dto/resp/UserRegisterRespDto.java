package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * User registration Response DTO
 *
 * @author :Contanstin
 * @create 2023/6/11 22:47
 */
@Data
@Builder
public class UserRegisterRespDto {

    @Schema(description = "User ID")
    private Long uid;

    @Schema(description = "User token")
    private String token;
}
