package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * User information Request DTO
 *
 * @author :Contanstin
 * @create 2023/6/14 21:11
 */
@Data
public class UserInfoReqDto {

    private Long userId;
}
