package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.BookChapter;
import com.example.novel_backend.dao.entity.BookInfo;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.resp.BookCategoryRespDto;
import com.example.novel_backend.dto.resp.BookChapterRespDto;
import com.example.novel_backend.dto.resp.BookInfoRespDto;
import com.example.novel_backend.dto.resp.BookRankRespDto;
import com.example.novel_backend.manager.cache.BookCategoryCacheManager;
import com.example.novel_backend.manager.cache.BookRankCacheManager;
import com.example.novel_backend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Book Module Service Implementation Classes
 *
 * @author :Contanstin
 * @create 2023/6/29 14:08
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRankCacheManager bookRankCacheManager;

    private final BookInfoMapper bookInfoMapper;

    private final BookCategoryCacheManager bookCategoryCacheManager;

    @Override
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return RestResp.ok(bookRankCacheManager.listUpdateRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return RestResp.ok(bookRankCacheManager.listVisitRankBook());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return RestResp.ok(bookRankCacheManager.listNewestRankBooks());
    }

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto dto) {
        Page<BookInfoRespDto> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(page, dto);
        return RestResp.ok(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookInfos.stream().map(bookInfo -> BookInfoRespDto.builder()
                        .id(bookInfo.getId())
                        .categoryId(bookInfo.getCategoryId())
                        .categoryName(bookInfo.getCategoryName())
                        .picUrl(bookInfo.getPicUrl())
                        .bookName(bookInfo.getBookName())
                        .authorId(bookInfo.getAuthorId())
                        .authorName(bookInfo.getAuthorName())
                        .wordCount(bookInfo.getWordCount())
                        .visitCount(bookInfo.getVisitCount())
                        .collectCount(bookInfo.getCollectCount())
                        .lastChapterName(bookInfo.getLastChapterName())
                        .score(bookInfo.getScore())
                        .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                        .build()).toList()));
    }

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.ok(bookCategoryCacheManager.listCategory(workDirection));
    }

    @Override
    public RestResp<BookInfoRespDto> getBookById(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        return RestResp.ok(BookInfoRespDto.builder()
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
                .wordCount(bookInfo.getWordCount())
                .commentCount(bookInfo.getCommentCount())
                .collectCount(bookInfo.getCollectCount())
                .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                .build());
    }

    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        List<BookChapter> bookChapters = bookInfoMapper.listChapters(bookId);
        return RestResp.ok(bookChapters.stream().map(bookChapter ->
                BookChapterRespDto.builder()
                        .id(bookChapter.getId())
                        .chapterName(bookChapter.getChapterName())
                        .chapterWordCount(bookChapter.getWordCount()).build()).toList());
    }
}
