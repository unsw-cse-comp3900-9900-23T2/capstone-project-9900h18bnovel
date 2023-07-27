package com.example.novel_backend.controller.author;

import com.example.novel_backend.core.common.constant.ApiRouterConsts;
import com.example.novel_backend.core.common.constant.SystemConfigConsts;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dao.entity.AuthorInfo;
import com.example.novel_backend.dto.req.AuthorRegisterReqDto;
import com.example.novel_backend.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Author's - Author's Module API Controller
 *
 * @author :Contanstin
 * @create 2023/7/27 14:29
 */
@Tag(name = "AuthorController", description = "Author's - Author's Module")
@RestController
@RequestMapping(ApiRouterConsts.API_AUTHOR_URL_PREFIX)
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    /**
     * Author register interface
     */
    @Operation(summary = "Author register interface")
    @PostMapping("register")
    public RestResp<Void> register(@Valid @RequestBody AuthorRegisterReqDto dto) {
        return authorService.register(dto);
    }

    /**
     * Author info update interface
     */
    @Operation(summary = "Author info update interface")
    @PostMapping("update")
    public RestResp<Void> update(@Valid @RequestBody AuthorRegisterReqDto dto) {
        return authorService.update(dto);
    }
    /**
     * Get author info interface
     */
    @Operation(summary = "Get author info interface")
    @GetMapping("get_author_info")
    public RestResp<AuthorInfo> getAuthorInfo(@Parameter(description = "userId", required = true) Long userId){
        return authorService.getAuthorInfo(userId);
    }
}
