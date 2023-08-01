package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Fan fiction publish request dto
 *
 * @author :Contanstin
 * @create 2023/8/1 22:56
 */
@Data
public class FanficPublishReqDto {

    @Schema(description = "User ID", required = true)
    @NotNull(message="User ID can not be empty！")
    private Long userId;

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;

    @Schema(description = "Fan fiction content", required = true)
    @NotBlank(message="Content can not be empty！")
    private String fanficContent;
}
