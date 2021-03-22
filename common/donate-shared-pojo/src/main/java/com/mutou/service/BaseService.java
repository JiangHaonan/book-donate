package com.mutou.service;

import com.github.pagehelper.PageInfo;
import com.mutou.pojo.PagedGridResult;

import java.util.List;

public class BaseService {

    /**
     * 通过PageHelper查询后封装
     * @param list
     * @param page
     * @return
     */
    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    /**
     * 手动查询后的封装
     * @param list
     * @param page
     * @param total
     * @param record
     * @return
     */
    public PagedGridResult setterPagedGrid(List<?> list, Integer page, Integer total, Long record) {
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(total);
        grid.setRecords(record);
        return grid;
    }

    /**
     * 空数据Page
     * @return
     */
    public PagedGridResult emptyData(Integer page) {
        return setterPagedGrid(null, page, 0, 0L);
    }

}
