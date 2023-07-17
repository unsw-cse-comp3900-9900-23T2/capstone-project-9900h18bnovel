package com.example.novel_backend.dto.req;

import com.example.novel_backend.core.common.req.PageReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Book chapter request dto
 *
 * @author :Contanstin
 * @create 2023/7/17 21:51
 */
@Data
public class BookChapterReqDto extends PageReqDto {

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;
}
