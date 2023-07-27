package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dto.req.AuthorRegisterReqDto;

/**
 * Author service module
 *
 * @author :Contanstin
 * @create 2023/7/27 14:34
 */
public interface AuthorService {

    /**
     * Author register
     *
     * @param dto register dto
     * @return void
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);

    /**
     * Author update
     *
     * @param dto update dto
     * @return void
     */
    RestResp<Void> update(AuthorRegisterReqDto dto);

    /**
     * Get author information
     * @param userId user id
     * @return Author info
     */
    RestResp<AuthorInfo> getAuthorInfo(Long userId);
}
