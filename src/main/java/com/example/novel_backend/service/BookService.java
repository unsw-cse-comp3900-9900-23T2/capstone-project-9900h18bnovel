package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.BookComment;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.*;

import java.util.List;

/**
 * Book module Service
 * @author :Contanstin
 * @create 2023/6/29 14:07
 */
public interface BookService {

    /**
     * Book Update List
     *
     * @return Book Update List
     */
    RestResp<List<BookRankRespDto>> listUpdateRankBooks();

    /**
     * Book visit list
     * @return Book visit list
     */
    RestResp<List<BookRankRespDto>> listVisitRankBooks();

    /**
     * Book newest rank list
     * @return Book newest rank list
     */
    RestResp<List<BookRankRespDto>> listNewestRankBooks();

    /**
     * Book search
     * @param dto Search condition
     * @return search result
     */
    RestResp<List<BookInfoRespDto>> searchBooks(BookSearchReqDto dto);

    /**
     * Book category List Search
     * @param workDirection Direction of work;0 - Male 1 - Female
     * @return Category list
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    /**
     * Book details
     * @param bookId book id
     * @return Book details
     */
    RestResp<BookInfoRespDto> getBookById(Long bookId);

    /**
     * Book chapter list
     * @param dto Book chapter dto
     * @return Book chapter list
     */
    RestResp<PageRespDto<BookChapterRespDto>> listChapters(BookChapterReqDto dto);

    /**
     * Get comment
     * @param dto get comment id
     * @return Comment
     */
    RestResp<BookComment> getCommentById(UserCommentReqDto dto);

    /**
     * List all comments
     * @param dto Comments dto
     * @return All comments
     */
    RestResp<PageRespDto<BookCommentRespDto>> listComments(UserCommentReqDto dto);

    /**
     * Post comment
     * @param dto comment dto
     * @return void
     */
    RestResp<Void> newComment(UserCommentReqDto dto);

    /**
     * Update comment
     * @param dto update comment dto
     * @return void
     */
    RestResp<Void> updateComment(UserCommentReqDto dto);

    /**
     * Delete comment
     * @param commentId Comment id
     * @return void
     */
    RestResp<Void> deleteComment(Long commentId);

    /**
     * Collect book
     * @param dto user collect book dto
     * @return void
     */
    RestResp<Void> collect(UserCollectReqDto dto);

    /**
     * Cancel collect
     * @param dto User collect book dto
     * @return void
     */
    RestResp<Void> cancelCollect(UserCollectReqDto dto);

    /**
     * Add visit count
     *
     * @param bookId 小说ID
     * @return 成功状态
     */
    RestResp<Void> addVisitCount(Long bookId);

    /**
     * Book content information
     * @param dto book content dto
     * @return Book content information
     */
    RestResp<BookContentRespDto> getBookContent(BookContentReqDto dto);

    /**
     * Get Previous Chapter id
     *
     * @param chapterId chapter ID
     * @return Previous Chapter id
     */
    RestResp<Long> getPreChapterId(Long chapterId);

    /**
     * Get next chapter id
     *
     * @param chapterId chapter ID
     * @return Next chapter id
     */
    RestResp<Long> getNextChapterId(Long chapterId);

    /**
     * Publish fan fiction
     * @param dto fan fiction dto
     * @return void
     */
    RestResp<Void> publishFanfic(FanficPublishReqDto dto);

    /**
     * Update fan fiction
     * @param dto fan fiction dto
     * @return void
     */
    RestResp<Void> updateFanfic(FanficUpdateReqDto dto);

    /**
     * Delete fan fiction
     * @param fanficId fan fiction id
     * @return void
     */
    RestResp<Void> deleteFanfic(Long fanficId);

    /**
     * List all book fanfic
     * @param dto book fanfic dto
     * @return paged book fanfic
     */
    RestResp<PageRespDto<BookFanficRespDto>> listBookFanfic(BookFanficReqDto dto);


    /**
     * List User book fanfic
     * @param dto book fanfic dto
     * @return paged book fanfic
     */
    RestResp<PageRespDto<BookFanficRespDto>> listUserBookFanfic(BookFanficReqDto dto);

    /**
     * Get fanfic by fanfic id
     * @param fanficId fanfic id
     * @return fanfic information
     */
    RestResp<BookFanficRespDto> getBookFanficById(Long fanficId);
}
