package com.mutou.delivery.controller;

import com.mutou.controller.BaseController;
import com.mutou.delivery.pojo.Delivery;
import com.mutou.delivery.service.DeliveryService;
import com.mutou.pojo.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "物流配送信息接口", tags = {"物流配送信息的相关接口"})
@RestController
@RequestMapping("delivery")
public class DeliveryController extends BaseController {

    @Autowired
    private DeliveryService deliveryService;

    @ApiOperation(value = "物流配送信息详情", notes = "物流配送信息详情", httpMethod = "GET")
    @GetMapping("/info/{deliveryId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "deliveryId", value = "物流配送Id", required = true)
            @PathVariable String deliveryId,
            HttpServletRequest request) {

        if (StringUtils.isBlank(deliveryId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        String userId = request.getHeader(UID_HEADER);
        if (userId == null) {
            return IMOOCJSONResult.ok(null);
        }

        Delivery delivery = deliveryService.queryByIdAndUserId(deliveryId, userId);

        return IMOOCJSONResult.ok(delivery);
    }

    @ApiOperation(value = "物流配送信息列表", notes = "物流配送详情", httpMethod = "GET")
    @GetMapping("/list")
    public IMOOCJSONResult list(HttpServletRequest request) {

        String userId = request.getHeader(UID_HEADER);
        List<Delivery> deliverys = deliveryService.queryListByUserId(userId);

        return IMOOCJSONResult.ok(deliverys);
    }
}
