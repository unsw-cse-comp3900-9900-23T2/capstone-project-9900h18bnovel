package com.example.novel_backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.novel_backend.dao.entity.BookChapter;

import java.util.List;

/**
 * Book chapter Mapper interface
 * @author :Contanstin
 * @create 2023/7/17 23:15
 */
public interface BookChapterMapper extends BaseMapper<BookChapter> {

    /**
     * Book chapter list
     * @param page mybatis-plus 分页对象
     * @param bookId book id
     * @return Book chapter list
     */
    List<BookChapter> listChapters(IPage<BookChapter> page, Long bookId);
}
