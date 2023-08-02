package com.example.novel_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.*;
import com.example.novel_backend.dao.mapper.*;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.*;
import com.example.novel_backend.manager.cache.BookCategoryCacheManager;
import com.example.novel_backend.manager.cache.BookChapterCacheManager;
import com.example.novel_backend.manager.cache.BookInfoCacheManager;
import com.example.novel_backend.manager.cache.BookRankCacheManager;
import com.example.novel_backend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final BookChapterMapper bookChapterMapper;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookContentMapper bookContentMapper;

    private final UserReadHistoryMapper userReadHistoryMapper;

    private final BookFanficMapper bookFanficMapper;

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
                .workDirection(bookInfo.getWorkDirection())
                .wordCount(bookInfo.getWordCount())
                .commentCount(bookInfo.getCommentCount())
                .collectCount(bookInfo.getCollectCount())
                .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                .build());
    }

    @Override
    public RestResp<PageRespDto<BookChapterRespDto>> listChapters(BookChapterReqDto dto) {
        IPage<BookChapter> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        List<BookChapter> bookChapters = bookChapterMapper.listChapters(page, dto.getBookId());
        return RestResp.ok(
                PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookChapters.stream().map(bookChapter ->
                BookChapterRespDto.builder()
                        .id(bookChapter.getId())
                        .chapterName(bookChapter.getChapterName())
                        .chapterNum(bookChapter.getChapterNum())
                        .chapterWordCount(bookChapter.getWordCount()).build()).toList()));
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
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        bookRankCacheManager.evictNewestRankCache();
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateComment(UserCommentReqDto dto) {
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", dto.getUserId())
                .eq("book_id", dto.getBookId()).last("LIMIT 1");
        BookComment bookComment = new BookComment();
        bookComment.setCommentContent(dto.getCommentContent());
        bookComment.setScore(dto.getScore());
        bookComment.setUpdateTime(LocalDateTime.now());
        bookCommentMapper.update(bookComment, queryWrapper);
        // update book information
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        float score = 0.0f;
        score = (bookInfo.getScore() * (bookInfo.getCommentCount() - 1) + dto.getScore())
                / (bookInfo.getCommentCount());
        bookInfo.setScore(score);
        bookInfoMapper.updateById(bookInfo);
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        bookRankCacheManager.evictNewestRankCache();
        return RestResp.ok();

    }

    @Override
    public RestResp<Void> deleteComment(Long commentId) {
        BookComment bookComment = bookCommentMapper.selectById(commentId);
        bookCommentMapper.deleteById(commentId);
        BookInfo bookInfo = bookInfoMapper.selectById(bookComment.getBookId());
        float score = 0.0f;
        if(bookInfo.getCommentCount() != 1) {
            score = (bookInfo.getScore() * (bookInfo.getCommentCount()) - bookComment.getScore())
                    / (bookInfo.getCommentCount() - 1);
        }
        bookInfo.setScore(score);
        bookInfo.setCommentCount(bookInfo.getCommentCount() - 1);
        bookInfoMapper.updateById(bookInfo);
        bookInfoCacheManager.evictBookInfoCache(bookComment.getBookId());
        bookRankCacheManager.evictUpdateRankCache();
        bookRankCacheManager.evictNewestRankCache();
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

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        bookInfo.setVisitCount(bookInfo.getVisitCount() + 1);
        bookInfoMapper.updateById(bookInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<BookContentRespDto> getBookContent(BookContentReqDto dto) {
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(dto.getChapterId());
        BookInfoRespDto bookInfoRespDto = bookInfoCacheManager.getBookInfo(bookChapterRespDto.getBookId());
        QueryWrapper<BookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", dto.getChapterId()).last("limit 1");
        BookContent bookContent = bookContentMapper.selectOne(queryWrapper);
        String content = bookContent.getContent();
        if(dto.getUserId()!=null){
            QueryWrapper<UserReadHistory> userReadHistoryQueryWrapper = new QueryWrapper<>();
            userReadHistoryQueryWrapper.eq("user_id", dto.getUserId())
                    .eq("book_id", bookChapterRespDto.getBookId()).last("LIMIT 1");
            UserReadHistory userReadHistory = userReadHistoryMapper.selectOne(userReadHistoryQueryWrapper);
            if(userReadHistoryMapper.selectCount(userReadHistoryQueryWrapper) > 0){
                userReadHistory.setPreChapterId(dto.getChapterId());
                userReadHistoryMapper.updateById(userReadHistory);
            }else {
                UserReadHistory userReadHistory1 = new UserReadHistory();
                userReadHistory1.setUserId(dto.getUserId());
                userReadHistory1.setBookId(bookChapterRespDto.getBookId());
                userReadHistory1.setPreChapterId(dto.getChapterId());
                userReadHistory1.setCreateTime(LocalDateTime.now());
                userReadHistory1.setUpdateTime(LocalDateTime.now());
                userReadHistoryMapper.insert(userReadHistory1);
            }
        }
        return RestResp.ok(BookContentRespDto.builder()
                .bookInfo(bookInfoRespDto)
                .chapterInfo(bookChapterRespDto)
                .bookContent(content).build());
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // Get Previous Chapter id
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId)
                .lt("chapter_num", chapterNum)
                .orderByDesc("chapter_num")
                .last("limit 1");
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<Long> getNextChapterId(Long chapterId) {
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // Get Last Chapter id
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId)
                .gt("chapter_num", chapterNum)
                .orderByAsc("chapter_num")
                .last("limit 1");
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<Void> publishFanfic(FanficPublishReqDto dto) {
        BookFanfic bookFanfic = new BookFanfic();
        bookFanfic.setBookId(dto.getBookId());
        bookFanfic.setUserId(dto.getUserId());
        bookFanfic.setFanficContent(dto.getFanficContent());
        bookFanfic.setCreateTime(LocalDateTime.now());
        bookFanfic.setUpdateTime(LocalDateTime.now());
        bookFanficMapper.insert(bookFanfic);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateFanfic(FanficUpdateReqDto dto) {
        BookFanfic bookFanfic = bookFanficMapper.selectById(dto.getFanficId());
        bookFanfic.setFanficContent(dto.getFanficContent());
        bookFanfic.setUpdateTime(LocalDateTime.now());
        bookFanficMapper.updateById(bookFanfic);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteFanfic(Long fanficId) {
        bookFanficMapper.deleteById(fanficId);
        return RestResp.ok();
    }

    @Override
    public RestResp<PageRespDto<BookFanficRespDto>> listBookFanfic(BookFanficReqDto dto) {
        IPage<BookFanfic> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<BookFanfic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", dto.getBookId())
                .orderByDesc("update_time");
        IPage<BookFanfic> bookFanficPage = bookFanficMapper.selectPage(page, queryWrapper);
        List<BookFanfic> bookFanfics = bookFanficMapper.selectList(queryWrapper);
        List<Long> userIds = bookFanfics.stream().map(BookFanfic::getUserId).toList();
        // Get user information
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds);
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        Map<Long, UserInfo> userInfoMap =userInfos.stream()
                .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        return RestResp.ok(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookFanficPage.getRecords().stream().map(bookFanfic ->
                        BookFanficRespDto.builder()
                                .id(bookFanfic.getId())
                                .fanficContent(bookFanfic.getFanficContent())
                                .fanficUserId(bookFanfic.getUserId())
                                .fanficUserName(userInfoMap.get(bookFanfic.getUserId()).getUsername())
                                .fanficUserImage(userInfoMap.get(bookFanfic.getUserId()).getUserPhoto())
                                .fanficTime(bookFanfic.getUpdateTime())
                                .build()).toList()));
    }

    @Override
    public RestResp<PageRespDto<BookFanficRespDto>> listUserBookFanfic(BookFanficReqDto dto) {
        IPage<BookFanfic> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<BookFanfic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", dto.getBookId())
                .eq("user_id", dto.getUserId())
                .orderByDesc("update_time");
        IPage<BookFanfic> bookFanficPage = bookFanficMapper.selectPage(page, queryWrapper);
        UserInfo userInfo = userInfoMapper.selectById(dto.getUserId());
        return RestResp.ok(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookFanficPage.getRecords().stream().map(bookFanfic ->
                        BookFanficRespDto.builder()
                                .id(bookFanfic.getId())
                                .fanficContent(bookFanfic.getFanficContent())
                                .fanficUserId(bookFanfic.getUserId())
                                .fanficUserName(userInfo.getUsername())
                                .fanficUserImage(userInfo.getUserPhoto())
                                .fanficTime(bookFanfic.getUpdateTime())
                                .build()).toList()));
    }

    @Override
    public RestResp<BookFanficRespDto> getBookFanficById(Long fanficId) {
        BookFanfic bookFanfic = bookFanficMapper.selectById(fanficId);
        UserInfo userInfo = userInfoMapper.selectById(bookFanfic.getUserId());
        return RestResp.ok(
                BookFanficRespDto.builder()
                        .id(bookFanfic.getId())
                        .fanficContent(bookFanfic.getFanficContent())
                        .fanficUserId(bookFanfic.getUserId())
                        .fanficUserName(userInfo.getUsername())
                        .fanficUserImage(userInfo.getUserPhoto())
                        .fanficTime(bookFanfic.getUpdateTime())
                        .build()
        );
    }

}
