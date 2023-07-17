package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Book content request dto
 * @author :Contanstin
 * @create 2023/7/17 23:50
 */
@Data
public class BookContentReqDto {
    @Schema(description = "Chapter ID", required = true)
    @NotNull(message="Chapter ID can not be empty！")
    private Long chapterId;

    @Schema(description = "User ID")
    private Long userId;
}
