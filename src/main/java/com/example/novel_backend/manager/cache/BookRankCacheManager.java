package com.example.novel_backend.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel_backend.core.common.constant.CacheConsts;
import com.example.novel_backend.dao.entity.BookInfo;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dto.resp.BookRankRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Book Rankings Cache Management Category
 * @author :Contanstin
 * @create 2023/6/28 16:07
 */
@Component
@RequiredArgsConstructor
public class BookRankCacheManager {

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询小说点击榜列表，并放入缓存中
     */
//    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
//            value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
//    public List<BookRankRespDto> listVisitRankBook() {
//    }
//
//    private List<BookRankRespDto> listRankBooks(QueryWrapper<BookInfo> bookInfoQueryWrapper){
//        return bookInfoMapper.selectList(bookInfoQueryWrapper).stream().map(bookInfo -> {
//            BookRankRespDto respDto = new BookRankRespDto();
//            respDto.setId(bookInfo.getId());
//            respDto.setCategoryId(bookInfo.getCategoryId());
//            respDto.setPicUrl(bookInfo.getPicUrl());
//
//            return respDto;
//        }).toList();
//    }
}
