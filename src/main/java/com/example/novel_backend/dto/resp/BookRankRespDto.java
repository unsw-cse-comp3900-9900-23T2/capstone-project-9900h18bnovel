package com.example.novel_backend.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Book Rankings Responding to the DTO
 * @author :Contanstin
 * @create 2023/6/28 16:10
 */
@Data
public class BookRankRespDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "Book ID")
    private Long id;

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

    /**
     * Book image
     */
    @Schema(description = "Book image")
    private String picUrl;

    /**
     * Book name
     */
    @Schema(description = "Book name")
    private String bookName;

    /**
     * Author name
     */
    @Schema(description = "Author name")
    private String authorName;

    /**
     * Book description
     */
    @Schema(description = "书籍描述")
    private String bookDesc;

    /**
     * Total word count
     */
    @Schema(description = "Total word count")
    private Integer wordCount;

    /**
     * Visit count
     */
    @Schema(description = "Visit count")
    private Long visitCount;

    /**
     * Collect count
     */
    @Schema(description = "Collect count")
    private Long collectCount;

    /**
     * Book score
     */
    @Schema(description = "Book score")
    private Float score;

    /**
     * Latest Chapter Name
     */
    @Schema(description = "Latest Chapter Name")
    private String lastChapterName;

    /**
     * Latest chapter update time
     */
    @Schema(description = "Latest chapter update time")
    @JsonFormat(pattern = "MM/dd HH:mm")
    private LocalDateTime lastChapterUpdateTime;
}
