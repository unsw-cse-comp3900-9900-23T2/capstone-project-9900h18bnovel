package com.example.novel_backend.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Book Fanfic response dto
 *
 * @author :Contanstin
 * @create 2023/7/15 14:48
 */
@Data
@Builder
public class BookFanficRespDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Fanfic ID")
    private Long id;

    @Schema(description = "Fanfic content")
    private String fanficContent;

    @Schema(description = "User name")
    private String fanficUserName;

    @Schema(description = "User ID")
    private Long fanficUserId;

    @Schema(description = "User image")
    private String fanficUserImage;

    @Schema(description = "fanfic time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fanficTime;

}
