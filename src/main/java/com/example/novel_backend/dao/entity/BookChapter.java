package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Book Chapter
 *
 * @author :Contanstin
 * @create 2023/7/12 15:26
 */
@TableName("book_chapter")
@Data
public class BookChapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Book ID
     */
    private Long bookId;

    /**
     * Chapter num
     */
    private Integer chapterNum;

    /**
     * Chapter name
     */
    private String chapterName;

    /**
     * Chapter words
     */
    private Integer wordCount;

    /**
     * Create date
     */
    private LocalDateTime createTime;

    /**
     * Update date
     */
    private LocalDateTime updateTime;
}
