package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Book Recommendation
 * @author :Contanstin
 * @create 2023/6/21 15:59
 */
@TableName("home_book")
@Data
public class HomeBook {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Recommendation type;0-Rotary 1-Top bar 2-This week's top picks 3-Popular picks 4-Fine picks
     */
    private Integer type;

    /**
     * Recommended sorting
     */
    private Integer sort;

    /**
     * Recommended Novel ID
     */
    private Long bookId;

    /**
     * Create date
     */
    private LocalDateTime createTime;

    /**
     * Update date
     */
    private LocalDateTime updateTime;

}
