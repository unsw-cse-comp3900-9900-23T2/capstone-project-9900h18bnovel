package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dto.req.*;
import com.example.novel_backend.dto.resp.BookInfoRespDto;

/**
 * Author service module
 *
 * @author :Contanstin
 * @create 2023/7/27 14:34
 */
public interface AuthorService {

    /**
     * Author register
     *
     * @param dto register dto
     * @return void
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);

    /**
     * Author update
     *
     * @param dto update dto
     * @return void
     */
    RestResp<Void> update(AuthorRegisterReqDto dto);

    /**
     * Get author information
     * @param userId user id
     * @return Author info
     */
    RestResp<AuthorInfo> getAuthorInfo(Long userId);

    /**
     * Publish a new book
     *
     * @param dto book information
     * @return void
     */
    RestResp<Void> publishBook(BookPublishReqDto dto);

    /**
     * Update a new book
     *
     * @param dto book information
     * @return void
     */
    RestResp<Void> updateBook(BookUpdateReqDto dto);

    /**
     * Get author published books
     * @param dto page parameters
     * @return Book information
     */
    RestResp<PageRespDto<BookInfoRespDto>> listAuthorBooks(AuthorBooksReqDto dto);

    /**
     * Publish chapter
     * @param dto chapter information
     * @return void
     */
    RestResp<Void> publishChapter(ChapterPublishReqDto dto);

    /**
     * Update chapter
     * @param dto chapter information
     * @return void
     */
    RestResp<Void> updateChapter(ChapterUpdateReqDto dto);

    /**
     * Delete chapter
     *
     * @param  chapterId chapter id
     * @return void
     */
    RestResp<Void> deleteChapter(Long chapterId);
}
