package com.example.novel_backend.controller.front;

import com.example.novel_backend.core.common.constant.ApiRouterConsts;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.resp.HomeBookRespDto;
import com.example.novel_backend.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Frontend Portal - Home Module API Controller
 *
 * @author :Contanstin
 * @create 2023/6/21 15:32
 */
@Tag(name = "HomeController", description = "Front Page Portal - Home Module")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_HOME_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    /**
     * Home page recommendation books interface
     */
    @Operation(summary = "Home page recommendation books interface")
    @GetMapping("books")
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {
        return homeService.listHomeBooks();
    }
}
