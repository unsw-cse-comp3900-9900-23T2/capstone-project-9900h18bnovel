package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.constant.SystemConfigConsts;
import com.example.novel_backend.core.common.exception.BusinessException;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.core.util.JwtUtils;
import com.example.novel_backend.dao.entity.UserInfo;
import com.example.novel_backend.dao.mapper.UserInfoMapper;
import com.example.novel_backend.dto.req.EmailReqDto;
import com.example.novel_backend.dto.req.UserLoginReqDto;
import com.example.novel_backend.dto.req.UserRegisterReqDto;
import com.example.novel_backend.dto.resp.ImgVerifyCodeRespDto;
import com.example.novel_backend.dto.resp.UserLoginRespDto;
import com.example.novel_backend.dto.resp.UserRegisterRespDto;
import com.example.novel_backend.manager.redis.VerifyCodeManager;
import com.example.novel_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * User module - Service Implementation Classes
 *
 * @author :Contanstin
 * @create 2023/6/11 16:32
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VerifyCodeManager verifyCodeManager;

    private final UserInfoMapper userInfoMapper;

    private final JwtUtils jwtUtils;

    @Override
    public RestResp<UserRegisterRespDto> getEmailVerifyCode(EmailReqDto dto) throws IOException {
        verifyCodeManager.sendVerificationCode(dto.getEmail());
        return RestResp.ok(
                UserRegisterRespDto.builder().build()
        );
    }

    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
        // Verify that the graphic verification code is correct
        if(!verifyCodeManager.verifyCodeOk(dto.getEmail(), dto.getVelCode())){
            // Graphic CAPTCHA verification failure
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        // Check if mailbox is registered or not
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", dto.getEmail()).last("LIMIT 1");
        if (userInfoMapper.selectCount(queryWrapper) > 0){
            // Email is registered
            throw new BusinessException(ErrorCodeEnum.EMAIL_EXIST);
        }

        // Registration successful, save user information
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(dto.getEmail());
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName(dto.getUsername());
        userInfo.setPassword(
                DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)));
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setSalt("0");
        userInfoMapper.insert(userInfo);

        // Delete Captcha
        verifyCodeManager.removeVerifyCode(dto.getEmail());

        // Generate JWT and return
        return RestResp.ok(
                UserRegisterRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .build()
        );
    }

    @Override
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(
                ImgVerifyCodeRespDto.builder()
                        .sessionId(sessionId)
                        .img(verifyCodeManager.genImgVerifyCode(sessionId))
                        .build()
        );
    }

    @Override
    public RestResp<UserLoginRespDto> login(UserLoginReqDto dto) {
        // Verify that the graphic verification code is correct
        if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())) {
            // Graphic CAPTCHA verification failure
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", dto.getEmail()).last("LIMIT 1");
        if (userInfoMapper.selectCount(queryWrapper) == 0){
            // Email is registered
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        // Determine if the password is correct
        if(!userInfo.getPassword().
                equals(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)))){
            // Wrong password
            throw new BusinessException(ErrorCodeEnum.USER_PASSWORD_ERROR);
        }

        // Login successful, JWT generated and returned
        return RestResp.ok(
                UserLoginRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .userName(userInfo.getUsername())
                        .build()
        );



    }
}
