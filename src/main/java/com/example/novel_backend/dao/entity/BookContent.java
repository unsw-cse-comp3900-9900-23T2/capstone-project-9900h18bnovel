package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Book content
 *
 * @author :Contanstin
 * @create 2023/7/18 0:00
 */
@TableName("book_content")
@Data
public class BookContent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Chapter ID
     */
    private Long chapterId;

    /**
     * Book content
     */
    private String content;

    /**
     * Creation date
     */
    private LocalDateTime createTime;

    /**
     * Update date
     */
    private LocalDateTime updateTime;
}
