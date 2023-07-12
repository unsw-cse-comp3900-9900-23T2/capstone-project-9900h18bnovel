package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author :Contanstin
 * @create 2023/7/8 19:45
 */
@TableName("book_category")
@Data
public class BookCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Direction of work;0 - Male 1 - Female
     */
    private Integer workDirection;

    /**
     * Category name
     */
    private String name;

    /**
     * Sort
     */
    private Integer sort;

    /**
     * Create time
     */
    private LocalDateTime createTime;

    /**
     * Update time
     */
    private LocalDateTime updateTime;
}
