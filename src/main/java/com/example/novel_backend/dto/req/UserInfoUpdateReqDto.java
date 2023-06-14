package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author :Contanstin
 * @create 2023/6/14 21:57
 */
@Data
public class UserInfoUpdateReqDto {

    private Long userId;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Avatar")
    @Pattern(regexp="^/[^\s]{10,}\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM)$")
    private String userPhoto;

    @Schema(description = "User sex")
    @Min(value = 0)
    @Max(value = 1)
    private Integer userSex;
}
