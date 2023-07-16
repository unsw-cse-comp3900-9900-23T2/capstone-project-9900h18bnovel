package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.*;
import com.example.novel_backend.dao.mapper.BookCollectMapper;
import com.example.novel_backend.dao.mapper.BookCommentMapper;
import com.example.novel_backend.dao.mapper.BookInfoMapper;
import com.example.novel_backend.dao.mapper.UserInfoMapper;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.req.UserCollectReqDto;
import com.example.novel_backend.dto.req.UserCommentReqDto;
import com.example.novel_backend.dto.resp.*;
import com.example.novel_backend.manager.cache.BookCategoryCacheManager;
import com.example.novel_backend.manager.cache.BookRankCacheManager;
import com.example.novel_backend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final BookCommentMapper bookCommentMapper;

    private final UserInfoMapper userInfoMapper;

    private final BookCollectMapper bookCollectMapper;

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
    public RestResp<List<BookInfoRespDto>> searchBooks(BookSearchReqDto dto) {
        //
        List<BookInfo> bookAllInfos = bookInfoMapper.searchAllBooks(dto);
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(dto);

        return RestResp.ok(
                bookInfos.stream().map(bookInfo -> BookInfoRespDto.builder()
                        .id(bookInfo.getId())
                        .categoryId(bookInfo.getCategoryId())
                        .categoryName(bookInfo.getCategoryName())
                        .picUrl(bookInfo.getPicUrl())
                        .bookDesc(bookInfo.getBookDesc())
                        .bookName(bookInfo.getBookName())
                        .authorId(bookInfo.getAuthorId())
                        .authorName(bookInfo.getAuthorName())
                        .wordCount(bookInfo.getWordCount())
                        .visitCount(bookInfo.getVisitCount())
                        .collectCount(bookInfo.getCollectCount())
                        .lastChapterName(bookInfo.getLastChapterName())
                        .score(bookInfo.getScore())
                        .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                        .totalNum(bookAllInfos.size())
                        .build()).toList());
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

    @Override
    public RestResp<BookComment> getCommentById(UserCommentReqDto dto) {
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId())
                .eq("book_id", dto.getBookId()).last("LIMIT 1");
        BookComment bookComment = bookCommentMapper.selectOne(queryWrapper);
        return RestResp.ok(bookComment);
    }

    @Override
    public RestResp<PageRespDto<BookCommentRespDto>> listComments(UserCommentReqDto dto) {
        IPage<BookComment> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", dto.getBookId())
                .orderByDesc("update_time");
        IPage<BookComment> bookCommentPage = bookCommentMapper.selectPage(page, queryWrapper);
        List<BookComment> bookComments = bookCommentMapper.selectList(queryWrapper);
        List<Long> userIds = bookComments.stream().map(BookComment::getUserId).toList();
        // Get user information
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds);
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        Map<Long, UserInfo> userInfoMap =userInfos.stream()
                .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        return RestResp.ok(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookCommentPage.getRecords().stream().map(bookComment ->
                        BookCommentRespDto.builder()
                                .id(bookComment.getId())
                                .commentContent(bookComment.getCommentContent())
                                .commentUserId(bookComment.getUserId())
                                .score(bookComment.getScore())
                                .commentUserName(userInfoMap.get(bookComment.getUserId()).getUsername())
                                .commentUserImage(userInfoMap.get(bookComment.getUserId()).getUserPhoto())
                                .commentTime(bookComment.getUpdateTime()).build()).toList()));
    }

    @Override
    public RestResp<Void> newComment(UserCommentReqDto dto) {
        // User has already commented or not
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId())
                .eq("book_id", dto.getBookId()).last("LIMIT 1");
        BookComment bookComment = bookCommentMapper.selectOne(queryWrapper);
        if(bookComment != null) {
            return RestResp.fail(ErrorCodeEnum.USER_COMMENTED);
        }
        BookComment newComment = new BookComment();
        newComment.setBookId(dto.getBookId());
        newComment.setUserId(dto.getUserId());
        newComment.setCommentContent(dto.getCommentContent());
        newComment.setScore(dto.getScore());
        newComment.setCreateTime(LocalDateTime.now());
        newComment.setUpdateTime(LocalDateTime.now());
        bookCommentMapper.insert(newComment);
        // update book information
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        float score = 0.0f;
        score = (bookInfo.getScore() * bookInfo.getCommentCount() + dto.getScore())
                / (bookInfo.getCommentCount() + 1);
        bookInfo.setScore(score);
        bookInfo.setCommentCount(bookInfo.getCommentCount() + 1);
        bookInfoMapper.updateById(bookInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateComment(UserCommentReqDto dto) {
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId())
                .eq("book_id", dto.getBookId()).last("LIMIT 1");
        BookComment bookComment = new BookComment();
        bookComment.setCommentContent(dto.getCommentContent());
        bookComment.setUpdateTime(LocalDateTime.now());
        bookCommentMapper.update(bookComment, queryWrapper);
        // update book information
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        float score = 0.0f;
        score = (bookInfo.getScore() * (bookInfo.getCommentCount() - 1) + dto.getScore())
                / (bookInfo.getCommentCount());
        bookInfo.setScore(score);
        bookInfoMapper.updateById(bookInfo);
        return RestResp.ok();

    }

    @Override
    public RestResp<Void> deleteComment(Long commentId) {
        bookCommentMapper.deleteById(commentId);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> collect(UserCollectReqDto dto) {
        BookCollect bookCollect = new BookCollect();
        bookCollect.setBookId(dto.getBookId());
        bookCollect.setUserId(dto.getUserId());
        bookCollect.setCreateTime(LocalDateTime.now());
        bookCollect.setUpdateTime(LocalDateTime.now());
        bookCollectMapper.insert(bookCollect);
        // update book info collect number
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        bookInfo.setCollectCount(bookInfo.getCollectCount() + 1);
        bookInfoMapper.updateById(bookInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> cancelCollect(UserCollectReqDto dto) {
        QueryWrapper<BookCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId())
                .eq("book_id", dto.getBookId()).last("LIMIT 1");
        bookCollectMapper.delete(queryWrapper);
        // update book info collect number
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        bookInfo.setCollectCount(bookInfo.getCollectCount() - 1);
        bookInfoMapper.updateById(bookInfo);
        return null;
    }

}
