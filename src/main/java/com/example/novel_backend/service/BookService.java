package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.BookComment;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.req.UserCommentReqDto;
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
     * @param bookId book id
     * @return Book chapter list
     */
    RestResp<List<BookChapterRespDto>> listChapters(Long bookId);

    /**
     * Get comment
     * @param dto get comment id
     * @return
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
    
}
