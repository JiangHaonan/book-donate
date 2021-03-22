package com.mutou.area.service.impl;

import com.mutou.area.mapper.AreaMapper;
import com.mutou.area.pojo.Area;
import com.mutou.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Area queryAreaById(String areaId) {
        return areaMapper.selectByPrimaryKey(areaId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Area> queryAreaList() {
        return areaMapper.selectAll();
    }
}
