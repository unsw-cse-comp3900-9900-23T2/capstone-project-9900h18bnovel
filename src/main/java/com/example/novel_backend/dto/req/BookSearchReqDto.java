package com.example.novel_backend.dto.req;

import com.example.novel_backend.core.common.req.PageReqDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author :Contanstin
 * @create 2023/7/8 14:25
 */
@Data
public class BookSearchReqDto extends PageReqDto {

//    /**
//     * Page data number
//     */
//    @Parameter(description = "Page data number")
//    private Integer pageNum;

    /**
     * Search key word
     */
    @Parameter(description = "Search key word")
    private String keyword;

    /**
     * Book Direction
     */
    @Parameter(description = "Book Direction")
    private Integer workDirection;

    /**
     * category ID
     */
    @Parameter(description = "category ID")
    private Integer categoryId;

    /**
     * Book Status;0-Continued 1-Completed
     */
    @Parameter(description = "Book Status;0-Continued 1-Completed")
    private Integer bookStatus;

    /**
     * Minimum word
     */
    @Parameter(description = "Minimum word")
    private Integer wordCountMin;

    /**
     * Maximum word
     */
    @Parameter(description = "Maximum word")
    private Integer wordCountMax;

    /**
     * Minimum update time
     *
     * If a Get request is used and the object is received directly,
     * it can be formatted using the @DateTimeFormat annotation;
     *
     * If using a Post request, @RequestBody receives the request body parameter,
     * which by default parses the date format as yyyy-MM-dd HH:mm:ss .
     *
     * If you need to receive parameters in another format, you can use the @JsonFormat annotation
     * */
    @Parameter(description = "Minimum update time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeMin;

    /**
     * Sort method,
     * Popular - visit_count,
     * Collection - collect_count,
     * Score - score,
     * Update time - last_chapter_update_time
     *
     */
    @Parameter(description = "Sort methods, Popular - visit_count, " +
            "Collection - collect_count, Score - score, Update time - last_chapter_update_time")
    private String sort;
}
