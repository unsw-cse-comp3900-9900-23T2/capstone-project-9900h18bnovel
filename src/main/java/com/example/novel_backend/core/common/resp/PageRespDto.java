package com.example.novel_backend.core.common.resp;

import lombok.Data;

import java.util.List;

/**
 * @author :Contanstin
 * @create 2023/6/11 15:27
 */
@Data
public class PageRespDto<T> {

    /**
     * Page number
     */
    private final long pageNum;

    /**
     * Size per page
     */
    private final long pageSize;

    /**
     * Total page
     */
    private final long total;

    /**
     * Paged data sets
     */
    private final List<? extends T> list;

    /**
     * This constructor is used in a generic paging query scenario
     * Receive generic paging data and generic collections
     */
    public PageRespDto(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> PageRespDto<T> of(long pageNum, long pageSize, long total, List<T> list) {
        return new PageRespDto<>(pageNum, pageSize, total, list);
    }

    /**
     * Get the number of pages after paging
     */
    public long getPages() {
        if (this.pageSize == 0L) {
            return 0L;
        } else {
            long pages = this.total / this.pageSize;
            if (this.total % this.pageSize != 0L) {
                ++pages;
            }
            return pages;
        }
    }

}
