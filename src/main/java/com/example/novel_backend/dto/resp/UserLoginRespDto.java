package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * User login Response DTO
 *
 * @author :Contanstin
 * @create 2023/6/13 14:45
 */
@Data
@Builder
public class UserLoginRespDto {
    @Schema(description = "User ID")
    private Long uid;

    @Schema(description = "Username")
    private String userName;

    @Schema(description = "User token")
    private String token;

    /**
     * User Photo
     * */
    @Schema(description = "User Photo")
    private String userPhoto;
}
