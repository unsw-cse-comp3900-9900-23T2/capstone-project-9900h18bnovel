package com.example.novel_backend.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel_backend.core.common.constant.CacheConsts;
import com.example.novel_backend.dao.entity.BookInfo;
import com.example.novel_backend.dao.entity.HomeBook;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dao.mapper.HomeBookMapper;
import com.example.novel_backend.dto.resp.HomeBookRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Home page recommend book cache Manager
 *
 * @author :Contanstin
 * @create 2023/6/21 15:42
 */
@Component
@RequiredArgsConstructor
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * Check the front page for novel recommendations and put them in the cache
     * @return Home page book dto
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> listHomeBooks(){
        // Search for a book to recommend from the novel recommendation table on the home page
        QueryWrapper<HomeBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        List<HomeBook> homeBooks = homeBookMapper.selectList(queryWrapper);

        if(!homeBooks.isEmpty()){
            // Get recommend bookId List
            List<Long> bookIds = homeBooks.stream().map(HomeBook::getBookId).toList();
            // Search the list of book information by bookId
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in("id", bookIds);
            List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

            // Return HomeBookRespDto list data
            if(!bookInfos.isEmpty()){
                List<HomeBookRespDto> homeBookRespDtos = new ArrayList<>();
                Map<Long, BookInfo> bookInfoMap = bookInfos.stream().
                        collect(Collectors.toMap(BookInfo::getId, Function.identity()));
                for(HomeBook homeBook: homeBooks){
                    BookInfo bookInfo = bookInfoMap.get(homeBook.getBookId());
                    HomeBookRespDto homeBookRespDto = new HomeBookRespDto();
                    homeBookRespDto.setBookId(homeBook.getBookId());
                    homeBookRespDto.setType(homeBook.getType());
                    homeBookRespDto.setBookName(bookInfo.getBookName());
                    homeBookRespDto.setAuthorName(bookInfo.getAuthorName());
                    homeBookRespDto.setPicUrl(bookInfo.getPicUrl());
                    homeBookRespDto.setBookDesc(bookInfo.getBookDesc());
                    homeBookRespDtos.add(homeBookRespDto);
                }
                return homeBookRespDtos;
            }
        }
        return new ArrayList<HomeBookRespDto>();
    }
}
