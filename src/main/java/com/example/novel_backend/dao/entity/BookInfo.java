package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Novel Information
 *
 * @author :Contanstin
 * @create 2023/6/21 15:44
 */
@TableName("book_info")
@Data
public class BookInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Direction of work;0 - Male 1 - Female
     */
    private Integer workDirection;

    /**
     * Category ID
     */
    private Long categoryId;

    /**
     * Category Name
     */
    private String categoryName;

    /**
     * Novel cover address
     */
    private String picUrl;

    /**
     * Novel Name
     */
    private String bookName;

    /**
     * Writer id
     */
    private Long authorId;

    /**
     * Writer's name
     */
    private String authorName;

    /**
     * Book Description
     */
    private String bookDesc;

    /**
     * Score; Total:10 , True score = score/10
     */
    private Integer score;

    /**
     * Book Status;0-Continued 1-Completed
     */
    private Integer bookStatus;

    /**
     * Visits count
     */
    private Long visitCount;

    /**
     * Total number of words
     */
    private Integer wordCount;

    /**
     * Number of comments
     */
    private Integer commentCount;

    /**
     * Latest Chapter ID
     */
    private Long lastChapterId;

    /**
     * Latest Chapter Name
     */
    private String lastChapterName;

    /**
     * Latest chapter update time
     */
    private LocalDateTime lastChapterUpdateTime;

    /**
     * Whether to charge; 1 - charge 0 - free
     */
    private Integer isVip;

    /**
     * Creation date
     */
    private LocalDateTime createTime;

    /**
     * Update date
     */
    private LocalDateTime updateTime;

}
