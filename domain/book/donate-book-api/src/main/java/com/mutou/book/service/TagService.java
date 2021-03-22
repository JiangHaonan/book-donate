package com.mutou.book.service;

import com.mutou.book.pojo.Tags;

import java.util.List;

/**
 * 本模块自己使用的Service
 * 用于对标签（tag）的相关操作
 */
public interface TagService {

    public List<Tags> getTagList();

    public Tags getByTag(String tag);
}
