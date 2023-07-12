package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Book Categories Responding to DTO
 *
 * @author :Contanstin
 * @create 2023/7/8 18:23
 */
@Data
@Builder
public class BookCategoryRespDto {
    /**
     * Category ID
     */
    @Schema(description = "Category ID")
    private Long categoryId;

    /**
     * Category name
     */
    @Schema(description = "Category name")
    private String categoryName;
}
