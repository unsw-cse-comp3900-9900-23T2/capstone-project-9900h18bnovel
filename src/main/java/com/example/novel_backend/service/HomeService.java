package com.example.novel_backend.service;

import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.dto.resp.HomeBookRespDto;

import java.util.List;

/**
 * Home Modules Services
 *
 * @author :Contanstin
 * @create 2023/6/21 15:36
 */
public interface HomeService {

    /**
     * Check the list of recommended books on the home page
     *
     * @return Home books list of rest response res
     */
    RestResp<List<HomeBookRespDto>> listHomeBooks();

}
