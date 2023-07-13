package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * User post comment Dto
 * @author :Contanstin
 * @create 2023/7/13 14:03
 */
@Data
public class UserCommentReqDto {

    /**
     * userId
     */
    private Long userId;

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;

    @Schema(description = "Comment content", required = true)
    @NotBlank(message="Comment can not be empty！")
    private String commentContent;
}
