package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.constant.SystemConfigConsts;
import com.example.novel_backend.core.common.exception.BusinessException;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.core.util.JwtUtils;
import com.example.novel_backend.dao.entity.*;
import com.example.novel_backend.dao.mapper.*;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.*;
import com.example.novel_backend.manager.redis.VerifyCodeManager;
import com.example.novel_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User module - Service Implementation Classes
 *
 * @author :Contanstin
 * @create 2023/6/11 16:32
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final VerifyCodeManager verifyCodeManager;

    private final UserInfoMapper userInfoMapper;

    private final BookCollectMapper bookCollectMapper;

    private final BookInfoMapper bookInfoMapper;

    private final UserReadHistoryMapper userReadHistoryMapper;

    private final BookChapterMapper bookChapterMapper;

    private final JwtUtils jwtUtils;

    @Override
    public RestResp<Void> getEmailVerifyCode(EmailReqDto dto) throws IOException {
        verifyCodeManager.sendVerificationCode(dto.getEmail());
        return RestResp.ok();
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

        log.info("Register successful, username: {}", userInfo.getUsername());
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
        log.info("Login successful, username: {}", userInfo.getUsername());
        return RestResp.ok(
                UserLoginRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .userName(userInfo.getUsername())
                        .userPhoto(userInfo.getUserPhoto())
                        .build()
        );
    }

    @Override
    public RestResp<Void> resetPassword(ResetPasswordReqDto dto) {
        // Verify that the graphic verification code is correct
        if(!verifyCodeManager.verifyCodeOk(dto.getEmail(), dto.getVelCode())){
            // Graphic CAPTCHA verification failure
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        // Check if mailbox is registered or not
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", dto.getEmail()).last("LIMIT 1");
        if (userInfoMapper.selectCount(queryWrapper) == 0){
            // Email is registered
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(
                dto.getNewPassword().getBytes(StandardCharsets.UTF_8)));

        // update password
        log.info("Reset password successful, username: {}", userInfo.getUsername());
        userInfoMapper.update(userInfo, queryWrapper);
        return RestResp.ok();
    }

    @Override
    public RestResp<UserInfoRespDto> getUserInfo(UserInfoReqDto dto) {
        UserInfo userInfo = userInfoMapper.selectById(dto.getUserId());
        return RestResp.ok(
                UserInfoRespDto.builder()
                        .username(userInfo.getUsername())
                        .userPhoto(userInfo.getUserPhoto())
                        .userSex(userInfo.getUserSex())
                        .build()
        );
    }

    @Override
    public RestResp<Void> updateUserInfo(UserInfoUpdateReqDto dto) {
        UserInfo userInfo = userInfoMapper.selectById(dto.getUserId());
        userInfo.setUsername(dto.getUsername());
        userInfo.setUserSex(dto.getUserSex());
        userInfo.setUserPhoto(dto.getUserPhoto());
        userInfoMapper.updateById(userInfo);
        log.info("Update user information successful, username: {}", userInfo.getUsername());
        return RestResp.ok();
    }

    @Override
    public RestResp<List<UserCollectBookRespDto>> listUserCollect(Long userId) {
        QueryWrapper<BookCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_time");
        List<BookCollect> bookCollects = bookCollectMapper.selectList(queryWrapper);
        List<Long> bookIds = bookCollects.stream().map(BookCollect::getBookId).toList();
        if(bookIds.isEmpty()){
            return RestResp.ok(new ArrayList<>());
        }
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.in("id", bookIds);
        List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);
        // Order by collecting time
        bookInfos.sort(Comparator.comparingInt(book -> bookIds.indexOf(book.getId())));
        QueryWrapper<UserReadHistory> userReadHistoryQueryWrapper = new QueryWrapper<>();
        userReadHistoryQueryWrapper.eq("user_id", userId)
                .in("book_id", bookIds);
        List<UserReadHistory> userReadHistories = userReadHistoryMapper.selectList(userReadHistoryQueryWrapper);
        Map<Long, UserReadHistory> userReadHistoryMap = userReadHistories.stream()
                .collect(Collectors.toMap(UserReadHistory::getBookId, Function.identity()));
        return RestResp.ok(bookInfos.stream().map(bookInfo ->{
            UserCollectBookRespDto userCollectBookRespDto = new UserCollectBookRespDto();
            userCollectBookRespDto.setBookId(bookInfo.getId());
            userCollectBookRespDto.setCategoryId(bookInfo.getCategoryId());
            userCollectBookRespDto.setCategoryName(bookInfo.getCategoryName());
            userCollectBookRespDto.setPicUrl(bookInfo.getPicUrl());
            userCollectBookRespDto.setBookName(bookInfo.getBookName());
            userCollectBookRespDto.setAuthorId(bookInfo.getAuthorId());
            userCollectBookRespDto.setAuthorName(bookInfo.getAuthorName());
            userCollectBookRespDto.setScore(bookInfo.getScore());
            userCollectBookRespDto.setBookDesc(bookInfo.getBookDesc());
            userCollectBookRespDto.setBookStatus(bookInfo.getBookStatus());
            userCollectBookRespDto.setVisitCount(bookInfo.getVisitCount());
            userCollectBookRespDto.setWordCount(bookInfo.getWordCount());
            userCollectBookRespDto.setCommentCount(bookInfo.getCommentCount());
            userCollectBookRespDto.setCollectCount(bookInfo.getCollectCount());
            if(userReadHistoryMap.containsKey(bookInfo.getId())){
                userCollectBookRespDto.setPreChapterId(userReadHistoryMap
                        .get(bookInfo.getId()).getPreChapterId());
                BookChapter bookChapter = bookChapterMapper.selectById(userReadHistoryMap
                        .get(bookInfo.getId()).getPreChapterId());
                userCollectBookRespDto.setPreChapterName(bookChapter.getChapterName());
            } else {
                QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
                bookChapterQueryWrapper.eq("book_id", bookInfo.getId())
                        .orderByAsc("chapter_num").last("limit 1");
                BookChapter firstBookChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
                userCollectBookRespDto.setPreChapterId(firstBookChapter.getId());
                BookChapter bookChapter = bookChapterMapper.selectById(firstBookChapter.getId());
                userCollectBookRespDto.setPreChapterName(bookChapter.getChapterName());
            }
            return userCollectBookRespDto;
                }).toList());
    }


}
