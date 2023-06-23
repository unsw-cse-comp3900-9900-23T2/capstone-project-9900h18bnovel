package com.example.novel_backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author Mingjie
 */
@TableName("user_info")
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Email
     */
    private String email;

    /**
     * Username
     */
    private String username;

    /**
     * Login Password - Encryption
     */
    private String password;

    /**
     * Encrypted salt values
     */
    private String salt;

    /**
     * Nickname
     */
    private String nickName;

    /**
     * User avatar
     */
    private String userPhoto;

    /**
     * User gender;0 - male 1 - female
     */
    private Integer userSex;

    /**
     * User status;0-normal
     */
    private Integer status;

    /**
     * Create time
     */
    private LocalDateTime createTime;

    /**
     * Update time
     */
    private LocalDateTime updateTime;
}
