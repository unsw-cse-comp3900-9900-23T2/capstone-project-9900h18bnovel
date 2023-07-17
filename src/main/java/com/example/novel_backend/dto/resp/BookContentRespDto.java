package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Book content response dto
 *
 * @author :Contanstin
 * @create 2023/7/17 22:54
 */
@Data
@Builder
public class BookContentRespDto {

    /**
     * Book information
     */
    @Schema(description = "Book information")
    private BookInfoRespDto bookInfo;

    /**
     * Chapter information
     */
    @Schema(description = "Chapter information")
    private BookChapterRespDto chapterInfo;

    /**
     * Chapter content
     */
    @Schema(description = "Chapter content")
    private String bookContent;
}
