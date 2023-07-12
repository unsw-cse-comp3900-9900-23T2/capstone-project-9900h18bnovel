package com.example.novel_backend.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel_backend.core.common.constant.CacheConsts;
import com.example.novel_backend.dao.entity.BookCategory;
import com.example.novel_backend.dao.mapper.BookCategoryMapper;
import com.example.novel_backend.dto.resp.BookCategoryRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Book Categories Cache Management Category
 *
 * @author :Contanstin
 * @create 2023/7/8 19:34
 */
@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    /**
     * Search the list of books categories
     * according to the direction of the work and put it in the cache
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.BOOK_CATEGORY_LIST_CACHE_NAME)
    public List<BookCategoryRespDto> listCategory(Integer workDirection){
        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_direction", workDirection);
        return bookCategoryMapper.selectList(queryWrapper).stream().map(bookCategory ->
                BookCategoryRespDto.builder()
                        .categoryId(bookCategory.getId())
                        .categoryName(bookCategory.getName())
                        .build()).toList();
    }
}
