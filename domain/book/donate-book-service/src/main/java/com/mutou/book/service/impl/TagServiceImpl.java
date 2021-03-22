package com.mutou.book.service.impl;

import com.mutou.book.mapper.TagsMapper;
import com.mutou.book.pojo.Tags;
import com.mutou.book.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<Tags> getTagList() {
        return tagsMapper.selectAll();
    }

    @Override
    public Tags getByTag(String tag) {
        Example tagsExp = new Example(Tags.class);
        Example.Criteria criteria = tagsExp.createCriteria();
        criteria.andEqualTo("tag", tag);
        return tagsMapper.selectOneByExample(tagsExp);
    }
}
