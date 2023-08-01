package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Other User information response dto
 *
 * @author :Contanstin
 * @create 2023/8/1 22:42
 */
@Data
@Builder
public class UserOtherInfoRespDto {

    /**
     * Username
     * */
    @Schema(description = "Username")
    private String username;

    /**
     * User Photo
     * */
    @Schema(description = "User Photo")
    private String userPhoto;

    /**
     * User sex
     * */
    @Schema(description = "User sex")
    private Integer userSex;

    /**
     * User email
     */
    @Schema(description = "User email")
    private String email;
}
