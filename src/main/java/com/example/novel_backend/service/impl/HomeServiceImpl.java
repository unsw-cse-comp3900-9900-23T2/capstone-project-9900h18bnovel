package com.example.novel_backend.service.impl;

import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.resp.HomeBookRespDto;
import com.example.novel_backend.manager.cache.HomeBookCacheManager;
import com.example.novel_backend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Home Module Service Implementation Classes
 *
 * @author :Contanstin
 * @create 2023/6/23 22:53
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;

    @Override
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {
        return RestResp.ok(homeBookCacheManager.listHomeBooks());
    }
}
