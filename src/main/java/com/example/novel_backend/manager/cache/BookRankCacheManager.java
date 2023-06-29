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
     * Check the list of book hits and put them in the cache
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> listVisitRankBook() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByAsc("visit_count")
                .last("limit 30");
        return listRankBooks(bookInfoQueryWrapper);
    }

    /**
     * Check the list of new books and put it in the cache
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> listNewestRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("create_time")
                .last("limit 30");
        return listRankBooks(bookInfoQueryWrapper);
    }

    /**
     * Check the list of books updated and put them in the cache
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listUpdateRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("update_time")
                .last("limit 30");
        return listRankBooks(bookInfoQueryWrapper);
    }

    private List<BookRankRespDto> listRankBooks(QueryWrapper<BookInfo> bookInfoQueryWrapper){
        return bookInfoMapper.selectList(bookInfoQueryWrapper).stream().map(bookInfo -> {
            BookRankRespDto respDto = new BookRankRespDto();
            respDto.setId(bookInfo.getId());
            respDto.setCategoryId(bookInfo.getCategoryId());
            respDto.setPicUrl(bookInfo.getPicUrl());
            respDto.setBookName(bookInfo.getBookName());
            respDto.setAuthorName(bookInfo.getAuthorName());
            respDto.setBookDesc(bookInfo.getBookDesc());
            respDto.setWordCount(bookInfo.getWordCount());
            respDto.setVisitCount(bookInfo.getVisitCount());
            respDto.setCollectCount(bookInfo.getCollectCount());
            respDto.setScore(bookInfo.getScore());
            respDto.setLastChapterName(bookInfo.getLastChapterName());
            respDto.setLastChapterUpdateTime(bookInfo.getLastChapterUpdateTime());
            return respDto;
        }).toList();
    }
}
