package com.example.novel_backend.controller.front;

import com.example.novel_backend.core.common.constant.ApiRouterConsts;
import com.example.novel_backend.core.common.constant.SystemConfigConsts;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.ImgVerifyCodeRespDto;
import com.example.novel_backend.dto.resp.UserInfoRespDto;
import com.example.novel_backend.dto.resp.UserLoginRespDto;
import com.example.novel_backend.dto.resp.UserRegisterRespDto;
import com.example.novel_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * User module
 * @author :Contanstin
 * @create 2023/6/11 16:16
 */
@Tag(name = "UserController", description = "User module")
@SecurityRequirement(name = SystemConfigConsts.HTTP_AUTH_HEADER_NAME)
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get email verification code interface")
    @PostMapping("email_verify_code")
    public RestResp<Void> getEmailVerifyCode(@Valid @RequestBody EmailReqDto dto) throws IOException {
        return userService.getEmailVerifyCode(dto);
    }

    /**
     * User registration interface
     */
    @Operation(summary = "User registration interface")
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto dto) {
        return userService.register(dto);
    }

    /**
     * Get Image Captcha Interface
     */
    @Operation(summary = "Get image captcha interface")
    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        return userService.getImgVerifyCode();
    }

    /**
     * User login interface
     */
    @Operation(summary = "User login interface")
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto){
        return userService.login(dto);
    }

    /**
     * Reset Password interface
     */
    @Operation(summary = "Reset password interface")
    @PostMapping("reset_password")
    public RestResp<Void> resetPassword(@Valid @RequestBody ResetPasswordReqDto dto){
        return userService.resetPassword(dto);
    }

    /**
     * User information search interface
     */
    @Operation(summary = "User information search interface")
    @PostMapping("get_userInfo")
    public RestResp<UserInfoRespDto> getUserInfo(@Valid @RequestBody UserInfoReqDto dto){
        return userService.getUserInfo(dto);
    }

    /**
     * User information modification interface
     */
    @Operation(summary = "User information modification interface")
    @PutMapping("modify_userInfo")
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUpdateReqDto dto){
        return userService.updateUserInfo(dto);
    }
}
