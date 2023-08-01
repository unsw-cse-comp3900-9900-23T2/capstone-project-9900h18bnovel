package com.example.novel_backend.dto.req;

import com.example.novel_backend.core.common.req.PageReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Book fanfic request dto
 *
 * @author :Contanstin
 * @create 2023/8/1 23:54
 */
@Data
public class BookFanficReqDto extends PageReqDto {

    @Schema(description = "User ID", required = true)
    @NotNull(message="User ID can not be empty！")
    private Long userId;

    @Schema(description = "Book ID", required = true)
    @NotNull(message="Book ID can not be empty！")
    private Long bookId;
}
