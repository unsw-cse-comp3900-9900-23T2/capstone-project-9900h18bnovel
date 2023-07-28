package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Update book request dto
 *
 * @author :Contanstin
 * @create 2023/7/28 14:41
 */
@Data
public class BookUpdateReqDto {

    /**
     * Book id
     */
    @Schema(description = "Book id", required = true)
    @NotNull
    private Long bookId;

    /**
     * Direction of work;0 - Male 1 - Female
     */
    @Schema(description = "Direction of work;0 - Male 1 - Female", required = true)
    @NotNull
    private Integer workDirection;

    /**
     * Author id
     */
    @Schema(description = "Author id", required = true)
    @NotNull
    private Long authorId;

    /**
     * Category ID
     */
    @Schema(description = "Category ID", required = true)
    @NotNull
    private Long categoryId;

    /**
     * Category name
     */
    @Schema(description = "Category name", required = true)
    @NotBlank
    private String categoryName;

    /**
     * Book picture
     */
    @Schema(description = "Book picture", required = true)
    @NotBlank
    private String picUrl;

    /**
     * Book name
     */
    @Schema(description = "Book name", required = true)
    @NotBlank
    private String bookName;

    /**
     * Book description
     */
    @Schema(description = "Book description", required = true)
    @NotBlank
    private String bookDesc;

    /**
     * Book Status;0-Continued 1-Completed
     */
    @Schema(description = "Book Status;0-Continued 1-Completed")
    @NotBlank
    private Integer bookStatus;
}
