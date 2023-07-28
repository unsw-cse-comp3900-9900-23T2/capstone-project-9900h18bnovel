package com.example.novel_backend.dto.req;

import com.example.novel_backend.core.common.req.PageReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Get author books
 *
 * @author :Contanstin
 * @create 2023/7/28 14:53
 */
@Data
public class AuthorBooksReqDto extends PageReqDto {

    /**
     * Author id
     */
    @Schema(description = "Author id", required = true)
    @NotNull
    private Long authorId;
}
