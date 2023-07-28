package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dao.entity.BookInfo;
import com.example.novel_backend.dao.mapper.AuthorInfoMapper;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dto.req.AuthorBooksReqDto;
import com.example.novel_backend.dto.req.AuthorRegisterReqDto;
import com.example.novel_backend.dto.req.BookPublishReqDto;
import com.example.novel_backend.dto.req.BookUpdateReqDto;
import com.example.novel_backend.dto.resp.BookInfoRespDto;
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

    private final BookInfoMapper bookInfoMapper;

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
        UpdateWrapper<BookInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("author_id", dto.getAuthorId());
        updateWrapper.set("author_name", dto.getPenName());
        bookInfoMapper.update(null, updateWrapper);
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

    @Override
    public RestResp<Void> publishBook(BookPublishReqDto dto) {
        // Book exists or not
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_name", dto.getBookName()).eq("author_id", dto.getAuthorId());
        if (bookInfoMapper.selectCount(queryWrapper) > 0) {
            return RestResp.fail(ErrorCodeEnum.AUTHOR_BOOK_NAME_EXIST);
        }

        AuthorInfo authorInfo = authorInfoMapper.selectById(dto.getAuthorId());
        BookInfo bookInfo = new BookInfo();
        bookInfo.setWorkDirection(dto.getWorkDirection());
        bookInfo.setCategoryId(dto.getCategoryId());
        bookInfo.setCategoryName(dto.getCategoryName());
        bookInfo.setPicUrl(dto.getPicUrl());
        bookInfo.setBookName(dto.getBookName());
        bookInfo.setAuthorId(dto.getAuthorId());
        bookInfo.setAuthorName(authorInfo.getPenName());
        bookInfo.setBookDesc(dto.getBookDesc());
        bookInfo.setCreateTime(LocalDateTime.now());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.insert(bookInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateBook(BookUpdateReqDto dto) {
        AuthorInfo authorInfo = authorInfoMapper.selectById(dto.getAuthorId());
        BookInfo bookInfo =bookInfoMapper.selectById(dto.getBookId());
        bookInfo.setWorkDirection(dto.getWorkDirection());
        bookInfo.setCategoryId(dto.getCategoryId());
        bookInfo.setCategoryName(dto.getCategoryName());
        bookInfo.setPicUrl(dto.getPicUrl());
        bookInfo.setBookName(dto.getBookName());
        bookInfo.setAuthorId(dto.getAuthorId());
        bookInfo.setAuthorName(authorInfo.getPenName());
        bookInfo.setBookDesc(dto.getBookDesc());
        bookInfo.setCreateTime(LocalDateTime.now());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.updateById(bookInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteBook(Long bookId) {
        bookInfoMapper.deleteById(bookId);
        return RestResp.ok();
    }

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> listAuthorBooks(AuthorBooksReqDto dto) {
        IPage<BookInfo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", dto.getAuthorId())
                .orderByDesc("update_time");
        IPage<BookInfo> bookInfoPage = bookInfoMapper.selectPage(page, queryWrapper);
        return RestResp.ok(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookInfoPage.getRecords().stream().map(bookInfo -> BookInfoRespDto.builder()
                .id(bookInfo.getId())
                .categoryId(bookInfo.getCategoryId())
                .categoryName(bookInfo.getCategoryName())
                .picUrl(bookInfo.getPicUrl())
                .bookName(bookInfo.getBookName())
                .authorId(bookInfo.getAuthorId())
                .authorName(bookInfo.getAuthorName())
                .score(bookInfo.getScore())
                .bookDesc(bookInfo.getBookDesc())
                .bookStatus(bookInfo.getBookStatus())
                .visitCount(bookInfo.getVisitCount())
                .workDirection(bookInfo.getWorkDirection())
                .wordCount(bookInfo.getWordCount())
                .commentCount(bookInfo.getCommentCount())
                .collectCount(bookInfo.getCollectCount())
                .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                .build()).toList()));
    }
}
