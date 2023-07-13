package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Book comment
 * @author :Contanstin
 * @create 2023/6/24 16:16
 */
@TableName("book_comment")
@Data
public class BookComment {

    /**
     * Primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Book ID
     */
    private Long bookId;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Comment content
     */
    private String commentContent;

    /**
     * Book score
     */
    private Float score;

    /**
     * Create Time
     */
    private LocalDateTime createTime;

    /**
     * Update Time
     */
    private LocalDateTime updateTime;

}
