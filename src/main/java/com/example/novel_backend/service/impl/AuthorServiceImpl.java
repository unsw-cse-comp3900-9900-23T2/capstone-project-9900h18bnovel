package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dao.entity.BookChapter;
import com.example.novel_backend.dao.entity.BookContent;
import com.example.novel_backend.dao.entity.BookInfo;
import com.example.novel_backend.dao.mapper.AuthorInfoMapper;
import com.example.novel_backend.dao.mapper.BookChapterMapper;
import com.example.novel_backend.dao.mapper.BookContentMapper;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.BookChapterRespDto;
import com.example.novel_backend.dto.resp.BookInfoRespDto;
import com.example.novel_backend.manager.cache.BookChapterCacheManager;
import com.example.novel_backend.manager.cache.BookInfoCacheManager;
import com.example.novel_backend.manager.cache.BookRankCacheManager;
import com.example.novel_backend.manager.cache.HomeBookCacheManager;
import com.example.novel_backend.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookRankCacheManager bookRankCacheManager;

    private final HomeBookCacheManager homeBookCacheManager;

    private final BookChapterMapper bookChapterMapper;

    private final BookContentMapper bookContentMapper;

    private final BookChapterCacheManager bookChapterCacheManager;

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
        bookRankCacheManager.evictNewestRankCache();
        homeBookCacheManager.evictHomeBooks();
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
        bookInfo.setBookStatus(dto.getBookStatus());
        bookInfo.setCreateTime(LocalDateTime.now());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.updateById(bookInfo);
        // delete cache
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        homeBookCacheManager.evictHomeBooks();
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

    @Override
    public RestResp<Void> publishChapter(ChapterPublishReqDto dto) {
        // get newest chapter number
        String content = dto.getChapterContent();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9'-]+");
        Matcher matcher = pattern.matcher(content);
        int wordCount = 0;
        while (matcher.find()) {
            wordCount++;
        }

        int chapterNum = 1;
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", dto.getBookId())
                .orderByDesc("chapter_num").last("limit 1");
        BookChapter bookChapter = bookChapterMapper.selectOne(queryWrapper);
        if(Objects.nonNull(bookChapter)){
            chapterNum = bookChapter.getChapterNum() + 1;
        }
        BookChapter newBookChapter = new BookChapter();
        newBookChapter.setBookId(dto.getBookId());
        newBookChapter.setChapterNum(chapterNum);
        newBookChapter.setChapterName(dto.getChapterName());
        newBookChapter.setWordCount(wordCount);
        newBookChapter.setCreateTime(LocalDateTime.now());
        newBookChapter.setUpdateTime(LocalDateTime.now());
        bookChapterMapper.insert(newBookChapter);

        BookContent bookContent = new BookContent();
        bookContent.setChapterId(newBookChapter.getId());
        bookContent.setContent(dto.getChapterContent());
        bookContent.setCreateTime(LocalDateTime.now());
        bookContent.setUpdateTime(LocalDateTime.now());
        bookContentMapper.insert(bookContent);

        //Update book information
        BookInfo oldBookInfo = bookInfoMapper.selectById(dto.getBookId());
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(dto.getBookId());
        bookInfo.setLastChapterId(newBookChapter.getId());
        bookInfo.setLastChapterName(newBookChapter.getChapterName());
        bookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        bookInfo.setWordCount(oldBookInfo.getWordCount() + newBookChapter.getWordCount());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.updateById(bookInfo);
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        homeBookCacheManager.evictHomeBooks();
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateChapter(ChapterUpdateReqDto dto) {
        String content = dto.getChapterContent();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9'-]+");
        Matcher matcher = pattern.matcher(content);
        int wordCount = 0;
        while (matcher.find()) {
            wordCount++;
        }

        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(dto.getBookId());
        BookChapterRespDto chapterInfo = bookChapterCacheManager.getChapter(dto.getChapterId());
        // Update book info
        BookInfo newBookInfo = new BookInfo();
        newBookInfo.setId(dto.getBookId());
        newBookInfo.setWordCount(bookInfo.getWordCount() -
                chapterInfo.getChapterWordCount() + wordCount);
        newBookInfo.setUpdateTime(LocalDateTime.now());
        if(dto.getChapterId().equals(bookInfo.getLastChapterId())){
            newBookInfo.setLastChapterName(dto.getChapterName());
            newBookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        }
        bookInfoMapper.updateById(newBookInfo);
        // Update chapter info
        BookChapter newChapter = new BookChapter();
        newChapter.setId(chapterInfo.getId());
        newChapter.setChapterName(dto.getChapterName());
        newChapter.setBookId(dto.getBookId());
        newChapter.setWordCount(wordCount);
        newChapter.setUpdateTime(LocalDateTime.now());
        bookChapterMapper.updateById(newChapter);
        // Update Content
        BookContent newContent = new BookContent();
        newContent.setContent(dto.getChapterContent());
        newContent.setUpdateTime(LocalDateTime.now());
        QueryWrapper<BookContent> bookContentQueryWrapper = new QueryWrapper<>();
        bookContentQueryWrapper.eq("chapter_id", dto.getChapterId());
        bookContentMapper.update(newContent, bookContentQueryWrapper);
        bookChapterCacheManager.evictBookChapterCache(dto.getChapterId());
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteChapter(Long chapterId) {
        BookChapterRespDto chapterInfo = bookChapterCacheManager.getChapter(chapterId);
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(chapterInfo.getBookId());
        bookChapterMapper.deleteById(chapterId);
        QueryWrapper<BookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId).last("limit 1");
        bookContentMapper.delete(queryWrapper);
        // Update Book info
        BookInfo newBookInfo = new BookInfo();
        newBookInfo.setId(chapterInfo.getBookId());
        newBookInfo.setWordCount(bookInfo.getWordCount() - chapterInfo.getChapterWordCount());
        newBookInfo.setUpdateTime(LocalDateTime.now());
        if(chapterId.equals(bookInfo.getLastChapterId())){
            // Update the newest chapter info
            QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
            bookChapterQueryWrapper.eq("book_id", chapterInfo.getBookId())
                    .orderByDesc("chapter_num").last("limit 1");
            BookChapter bookChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
            Long lastChapterId = 0L;
            String lastChapterName = "";
            LocalDateTime lastChapterUpdateTime = null;
            if (Objects.nonNull(bookChapter)) {
                lastChapterId = bookChapter.getId();
                lastChapterName = bookChapter.getChapterName();
                lastChapterUpdateTime = bookChapter.getUpdateTime();
            }
            newBookInfo.setLastChapterId(lastChapterId);
            newBookInfo.setLastChapterName(lastChapterName);
            newBookInfo.setLastChapterUpdateTime(lastChapterUpdateTime);
        }
        bookInfoMapper.updateById(newBookInfo);
        bookChapterCacheManager.evictBookChapterCache(chapterId);
        bookInfoCacheManager.evictBookInfoCache(chapterInfo.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        return RestResp.ok();
    }

}
