package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.req.EmailReqDto;
import com.example.novel_backend.dto.req.UserRegisterReqDto;
import com.example.novel_backend.dto.resp.UserRegisterRespDto;

import java.io.IOException;

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
    RestResp<UserRegisterRespDto> getEmailVerifyCode(EmailReqDto dto) throws IOException;

    /**
     * User register
     *
     * @param dto Registration Parameters
     * @return JWT
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

}
