package com.example.novel_backend.dto.req;

import com.example.novel_backend.core.common.req.PageReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * User comment request dto
 *
 * @author :Contanstin
 * @create 2023/7/13 14:03
 */
@Data
public class UserCommentReqDto extends PageReqDto {


    @Schema(description = "User ID", required = true)
    @NotNull(message="User ID can not be empty！")
    private Long userId;

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;

    @Schema(description = "Comment content", required = true)
    @NotBlank(message="Comment can not be empty！")
    private String commentContent;

    @Schema(description = "Book score")
    private Float score;
}