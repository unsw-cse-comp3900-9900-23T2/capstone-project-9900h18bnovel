package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Author register request dto
 *
 * @author :Contanstin
 * @create 2023/7/27 15:19
 */
@Data
public class AuthorRegisterReqDto {

    @Schema(description = "Author id")
    private Long authorId;

    @Schema(description = "User id")
    private Long userId;

    /**
     * Pen name
     */
    @Schema(description = "Pen name", required = true)
    @NotBlank(message = "Pen name can be be blank！")
    private String penName;

    /**
     * Author signature
     */
    @Schema(description = "Author signature", required = true)
    @NotBlank(message = "Author signature can be blank！")
    private String signature;
}
