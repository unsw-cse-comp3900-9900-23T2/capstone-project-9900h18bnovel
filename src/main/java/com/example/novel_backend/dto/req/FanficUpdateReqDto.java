package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Fan fiction update request dto
 *
 * @author :Contanstin
 * @create 2023/8/1 22:56
 */
@Data
public class FanficUpdateReqDto {

    /**
     * Fanfic id
     */
    @Schema(description = "Fanfic id", required = true)
    @NotNull
    private Long fanficId;

    @Schema(description = "Fan fiction content", required = true)
    @NotBlank(message="Content can not be empty！")
    private String fanficContent;
}
