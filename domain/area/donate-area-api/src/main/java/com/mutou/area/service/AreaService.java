package com.mutou.area.service;

import com.mutou.area.pojo.Area;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 捐赠地址，这个放在哪个服务都不合适，单独提出，以便后期扩展
 * 取名Area因为一般捐赠的是一个区域，不是一个具体地址（Address）
 */
@FeignClient("donate-area-service")
@RequestMapping("area-api")
public interface AreaService {

    /**
     * 根据ID查询区域
     * @param areaId
     */
    @GetMapping("area/{areaId}")
    public Area queryAreaById(@PathVariable("areaId") String areaId);

    /**
     * 获取区域列表
     */
    @GetMapping("list")
    public List<Area> queryAreaList();
}
