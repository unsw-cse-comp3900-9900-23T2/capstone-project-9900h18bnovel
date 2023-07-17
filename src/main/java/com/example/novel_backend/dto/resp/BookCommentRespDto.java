package com.example.novel_backend.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Book comment response dto
 *
 * @author :Contanstin
 * @create 2023/7/15 14:48
 */
@Data
@Builder
public class BookCommentRespDto {

    @Schema(description = "Comment ID")
    private Long id;

    @Schema(description = "Comment content")
    private String commentContent;

    @Schema(description = "User name")
    private String commentUserName;

    @Schema(description = "User ID")
    private Long commentUserId;

    @Schema(description = "User image")
    private String commentUserImage;

    @Schema(description = "Comment time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentTime;

    @Schema(description = "Book score")
    private Float score;
}
