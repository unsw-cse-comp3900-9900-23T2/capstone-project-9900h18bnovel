package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author :Contanstin
 * @create 2023/7/29 0:19
 */
@Data
public class ChapterUpdateReqDto {
    /**
     * Chapter ID
     */
    @Schema(description = "Chapter ID", required = true)
    @NotNull
    private Long chapterId;

    /**
     * Book ID
     */
    @Schema(description = "Book ID", required = true)
    @NotNull
    private Long bookId;

    /**
     * Chapter name
     */
    @NotBlank
    @Schema(description = "Chapter name", required = true)
    private String chapterName;

    /**
     * Chapter content
     */
    @Schema(description = "Chapter content", required = true)
    @NotBlank
    private String chapterContent;
}
