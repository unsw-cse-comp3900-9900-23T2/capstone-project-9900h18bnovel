package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * User collect request dto
 *
 * @author :Contanstin
 * @create 2023/7/16 15:45
 */
@Data
public class UserCollectReqDto {

    @Schema(description = "User ID", required = true)
    @NotNull(message="User ID can not be empty！")
    private Long userId;

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;
}
