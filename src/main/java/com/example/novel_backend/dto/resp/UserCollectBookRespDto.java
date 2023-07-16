package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * User collected book response dto
 *
 * @author :Contanstin
 * @create 2023/7/16 16:30
 */
@Data
@Builder
public class UserCollectBookRespDto {

    /**
     * ID
     */
    @Schema(description = "Book ID")
    private Long bookId;

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
}
