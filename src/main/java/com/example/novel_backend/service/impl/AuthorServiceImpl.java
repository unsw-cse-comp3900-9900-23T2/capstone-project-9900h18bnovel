package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dao.mapper.AuthorInfoMapper;
import com.example.novel_backend.dto.req.AuthorRegisterReqDto;
import com.example.novel_backend.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Author Module Service Implementation Classes
 *
 * @author :Contanstin
 * @create 2023/7/27 14:34
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoMapper authorInfoMapper;

    @Override
    public RestResp<Void> register(AuthorRegisterReqDto dto) {
        // Have registered or not
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId()).last("limit 1");
        if(authorInfoMapper.selectCount(queryWrapper) > 0){
            return RestResp.ok();
        }

        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setUserId(dto.getUserId());
        authorInfo.setPenName(dto.getPenName());
        authorInfo.setSignature(dto.getSignature());
        authorInfo.setCreateTime(LocalDateTime.now());
        authorInfo.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.insert(authorInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> update(AuthorRegisterReqDto dto) {
        AuthorInfo authorInfo = authorInfoMapper.selectById(dto.getAuthorId());
        authorInfo.setPenName(dto.getPenName());
        authorInfo.setSignature(dto.getSignature());
        authorInfo.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.updateById(authorInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<AuthorInfo> getAuthorInfo(Long userId) {
        // Have registered or not
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).last("limit 1");
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);
        return RestResp.ok(authorInfo);
    }
}
