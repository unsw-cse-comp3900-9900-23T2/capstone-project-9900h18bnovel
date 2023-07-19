package com.example.novel_backend.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Home Page Recommendations Responding to DTO
 * @author :Contanstin
 * @create 2023/6/23 21:25
 */
@Data
public class HomeBookRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Type;0-Rotary 1-Top bar 2-This week's top picks 3-Popular picks 4-Best picks
     */
    @Schema(description = "Type;0-Rotary 1-Top bar 2-This week's top picks 3-Popular picks 4-Best picks")
    private Integer type;

    /**
     * Recommend bookId
     */
    @Schema(description = "BookId")
    private Long bookId;

    /**
     * Book cover address
     */
    @Schema(description = "Book cover address")
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
    @Schema(description = "Book description")
    private String bookDesc;

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
     * Category Name
     */
    @Schema(description = "Category name")
    private String categoryName;

    @Schema(description = "Book comment")
    private List<BookCommentRespDto> bookComments;

}
