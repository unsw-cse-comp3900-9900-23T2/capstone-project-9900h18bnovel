package com.example.novel_backend.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Chapters Responding DTO
 *
 * @author :Contanstin
 * @create 2023/7/12 15:14
 */
@Data
@Builder
public class BookChapterRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Chapter ID
     */
    @Schema(description = "Chapter ID")
    private Long id;

    /**
     * Book ID
     */
    @Schema(description = "Book ID")
    private Long bookId;

    /**
     * Chapter number
     */
    @Schema(description = "Chapter Num")
    private Integer chapterNum;

    /**
     * Chapter name
     */
    @Schema(description = "Chapter name")
    private String chapterName;

    /**
     * Chapter words
     */
    @Schema(description = "Chapter words")
    private Integer chapterWordCount;

    /**
     * Chapter update time
     */
    @Schema(description = "Chapter update time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime chapterUpdateTime;
}
