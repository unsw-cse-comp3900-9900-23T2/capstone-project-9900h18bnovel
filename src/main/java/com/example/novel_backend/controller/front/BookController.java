package com.example.novel_backend.controller.front;

import com.example.novel_backend.core.common.constant.ApiRouterConsts;
import com.example.novel_backend.core.common.resp.PageRespDto;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.BookComment;
import com.example.novel_backend.dto.req.BookSearchReqDto;
import com.example.novel_backend.dto.req.UserCollectReqDto;
import com.example.novel_backend.dto.req.UserCommentReqDto;
import com.example.novel_backend.dto.resp.*;
import com.example.novel_backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

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
     * Book visit rank interface
     */
    @Operation(summary = "Book visit rank interface")
    @GetMapping("visit_rank")
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return bookService.listVisitRankBooks();
    }

    /**
     * Book newest rank interface
     */
    @Operation(summary = "Book newest rank interface")
    @GetMapping("newest_rank")
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return bookService.listNewestRankBooks();
    }

    /**
     * Book search interface
     */
    @Operation(summary = "Book search interface")
    @GetMapping("books")
    public RestResp<List<BookInfoRespDto>> searchBooks(
            @ParameterObject BookSearchReqDto dto) {
        return bookService.searchBooks(dto);
    }

    /**
     * Book Category List Search Interface
     */
    @Operation(summary = "Book Category List Search Interface")
    @GetMapping("category/list")
    public RestResp<List<BookCategoryRespDto>> listCategory(
            @Parameter(description = "workDirection", required = true) Integer workDirection) {
        return bookService.listCategory(workDirection);
    }

    /**
     * Book Information Query Interface
     */
    @Operation(summary = "Book Information Query Interface")
    @GetMapping("{id}")
    public RestResp<BookInfoRespDto> getBookById(
            @Parameter(description = "Book ID") @PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    /**
     * Book Chapter list Query Interface
     */
    @Operation(summary = "Book Chapter list Query Interface")
    @GetMapping("chapter/list")
    public RestResp<List<BookChapterRespDto>> listChapters(
            @Parameter(description = "Book ID") Long bookId) {
        return bookService.listChapters(bookId);
    }

    /**
     * Get comment by id interface
     */
    @Operation(summary = "Get comment interface")
    @PostMapping("get_comment")
    public RestResp<BookComment> getCommentById(@Valid @RequestBody UserCommentReqDto dto) {
        return bookService.getCommentById(dto);
    }

    /**
     * Get all comments interface
     */
    @Operation(summary = "Get all comments interface")
    @PostMapping("all_comments")
    public RestResp<PageRespDto<BookCommentRespDto>> listComments(@Valid @RequestBody UserCommentReqDto dto) {
        return bookService.listComments(dto);
    }

    /**
     * Post Comment
     */
    @Operation(summary = "Post Comment")
    @PostMapping("new_comment")
    public RestResp<Void> newComment(@Valid @RequestBody UserCommentReqDto dto) {
        return bookService.newComment(dto);
    }

    /**
     * Update comment interface
     */
    @Operation(summary = "Update comment interface")
    @PostMapping("update_comment")
    public RestResp<Void> updateComment(@Valid @RequestBody UserCommentReqDto dto) {
        return bookService.updateComment(dto);
    }

    /**
     * Delete comment interface
     */
    @Operation(summary = "Delete comment interface")
    @DeleteMapping("comment/{commentId}")
    public RestResp<Void> deleteComment(@Parameter(description = "Comment ID") @PathVariable Long commentId) {
        return bookService.deleteComment(commentId);
    }

    /**
     * User collect book interface
     */
    @Operation(summary = "User collect book interface")
    @PostMapping("collect")
    public RestResp<Void> collect(@Valid @RequestBody UserCollectReqDto dto) {
        return bookService.collect(dto);
    }

    /**
     * User cancel collect book interface
     */
    @Operation(summary = "User cancel collect book interface")
    @PostMapping("cancel_collect")
    public RestResp<Void> cancelCollect(@Valid @RequestBody UserCollectReqDto dto) {
        return bookService.cancelCollect(dto);
    }
}
