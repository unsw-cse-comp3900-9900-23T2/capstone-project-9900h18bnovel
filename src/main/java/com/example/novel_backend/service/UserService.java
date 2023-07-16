package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.UserInfo;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.*;

import java.io.IOException;
import java.util.List;

/**
 * User module - Service
 *
 * @author :Contanstin
 * @create 2023/6/11 16:31
 */
public interface UserService {
    /**
     * Get verification code via email
     *
     * @param dto Email
     * @return None
     * @throws IOException
     */
    RestResp<Void> getEmailVerifyCode(EmailReqDto dto) throws IOException;

    /**
     * User register
     *
     * @param dto Registration Parameters
     * @return JWT
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

    /**
     * Get Image Captcha
     *
     * @return Base64 encoded images
     * @throws IOException Captcha image generation failed
     */
    RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException;

    /**
     * User login
     * @param dto Login parameters
     * @return JWT + Username
     */
    RestResp<UserLoginRespDto> login(UserLoginReqDto dto);

    /**
     * Reset Password
     * @param dto Reset parameters
     * @return void
     */
    RestResp<Void> resetPassword(ResetPasswordReqDto dto);

    /**
     * User information search
     *
     * @param dto Search parameters
     * @return User information
     */
    RestResp<UserInfoRespDto> getUserInfo(UserInfoReqDto dto);

    /**
     * Modification of user information
     * @param dto User information
     * @return void
     */
    RestResp<Void> updateUserInfo(UserInfoUpdateReqDto dto);

    /**
     * List all user collected books
     * @param userId user id
     * @return All user collected books list
     */
    RestResp<List<UserCollectBookRespDto>> listUserCollect(Long userId);

}
