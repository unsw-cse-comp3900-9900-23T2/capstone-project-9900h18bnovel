package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.resp.BookInfoRespDto;
import com.example.novel_backend.dto.resp.BookRankRespDto;

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
     * Book search
     * @param dto Search condition
     * @return search result
     */
    RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto dto);
    
}
