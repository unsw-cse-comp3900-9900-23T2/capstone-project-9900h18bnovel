package com.example.novel_backend.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Book Information Responding to DTO
 *
 * @author :Contanstin
 * @create 2023/7/7 16:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInfoRespDto {
    /**
     * Page data number
     */
    @Schema(description = "Page data number")
    private Integer pageNum;


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
     * Book url
     */
    @Schema(description = "Book url")
    private String picUrl;

    /**
     * Book name
     */
    @Schema(description = "Book name")
    private String bookName;

    /**
     * Author id
     */
    @Schema(description = "Author id")
    private Long authorId;

    /**
     * Author name
     */
    @Schema(description = "Author name")
    private String authorName;

    /**
     * Book score
     */
    @Schema(description = "Book score")
    private Float score;

    /**
     * Book description
     */
    @Schema(description = "Book description")
    private String bookDesc;

    /**
     * Book Status;0-Continued 1-Completed
     */
    @Schema(description = "Book Status;0-Continued 1-Completed")
    private Integer bookStatus;

    /**
     * Visit count
     */
    @Schema(description = "Visit count")
    private Long visitCount;

    /**
     * word count
     */
    @Schema(description = "word count")
    private Integer wordCount;

    /**
     * Comment count
     */
    @Schema(description = "Comment count")
    private Integer commentCount;

    /**
     * Collect count
     */
    @Schema(description = "Collect count")
    private Long collectCount;

    /**
     * Book first chapter ID
     */
    @Schema(description = "Book first chapter Id")
    private Long firstChapterId;

    /**
     * Book last chapter ID
     */
    @Schema(description = "Book last chapter ID")
    private Long lastChapterId;

    /**
     * Book last chapter name
     */
    @Schema(description = "Book last chapter name")
    private String lastChapterName;

    /**
     * Book last update time
     */
    @Schema(description = "Book last update time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastChapterUpdateTime;
}
