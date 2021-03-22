package com.mutou.area.controller;

import com.mutou.area.pojo.Area;
import com.mutou.area.service.AreaService;
import com.mutou.controller.BaseController;
import com.mutou.pojo.IMOOCJSONResult;
import com.mutou.utils.DesensitizationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "捐赠地区相关", tags = {"捐赠地区相关的api接口"})
@RequestMapping("area")
@RestController
public class AreasController extends BaseController {

    @Autowired
    private AreaService areaService;

    @ApiOperation(value = "根据ID查询地区详情", notes = "根据ID查询地区详情", httpMethod = "GET")
    @GetMapping("/info/{areaId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "areaId", value = "地区id", required = true)
            @PathVariable String areaId) {

        if (StringUtils.isBlank(areaId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        Area area = areaService.queryAreaById(areaId);
        if (area == null) {
            return IMOOCJSONResult.ok(null);
        }
        area.setMobile(DesensitizationUtil.commonDisplay(area.getMobile()));

        return IMOOCJSONResult.ok(area);
    }

    @ApiOperation(value = "查询地区列表详情", notes = "查询地区列表详情", httpMethod = "GET")
    @GetMapping("list")
    public IMOOCJSONResult list() {

        List<Area> areaList = areaService.queryAreaList();
        for (Area area : areaList) {
            area.setMobile(DesensitizationUtil.commonDisplay(area.getMobile()));
        }

        return IMOOCJSONResult.ok(areaList);
    }
}
