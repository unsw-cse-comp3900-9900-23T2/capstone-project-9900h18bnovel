package com.example.novel_backend.core.common.req;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @author :Contanstin
 * @create 2023/6/11 15:21
 */
@Data
public class PageReqDto {

    /**
     * Request page number, default page 1
     */
    @Parameter(description = "Request page number, default page 1")
    private int pageNum = 1;

    /**
     * Size per page, default 10 items per page
     */
    @Parameter(description = "Size per page, default 10 items per page")
    private int pageSize = 10;

    /**
     * Whether to query all, default is not all, when true, pageNum and pageSize are invalid
     */
    @Parameter(hidden = true)
    private boolean fetchAll = false;
}
