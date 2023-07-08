package com.example.novel_backend.controller.front;

import com.example.novel_backend.core.common.constant.ApiRouterConsts;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.resp.BookInfoRespDto;
import com.example.novel_backend.dto.resp.BookRankRespDto;
import com.example.novel_backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Frontend Portal - Book Module API Controller
 *
 * @author :Contanstin
 * @create 2023/6/29 14:10
 */
@Tag(name = "BookController", description = "Front Page Portal - Book Module")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Book update rank interface
     */
    @Operation(summary = "Book update rank interface")
    @GetMapping("update_rank")
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return bookService.listUpdateRankBooks();
    }

    /**
     * Book search interface
     */
    @Operation(summary = "Book search interface")
    @GetMapping("books")
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(
            @ParameterObject BookSearchReqDto dto) {
        return bookService.searchBooks(dto);
    }
}
