package com.yh.controller.business;

import com.yh.domain.AjaxResult;
import com.yh.service.FangChanYaoHaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yaohuan
 * @version 1.0
 * @date 2021/07/31 07:37
 **/
@RestController
@Api(tags = "【房管局、发改委】")
public class FcyhController {
    public final Logger logger = LoggerFactory.getLogger(FcyhController.class);
    public final String fgjUrl = "http://60.173.254.126:8888";
    public final String fgwUrl = "http://220.178.124.94:8010";
//    public final String status = "正在登记";
//    public int date = 0; //今天
    public final String status = "暂未开始";
    public int date = 1; //明天

    @Autowired
    FangChanYaoHaoService fangChanYaoHaoService;

    @ApiOperation(value = "获取房产局、发改委信息", notes = "暂未开始的房产列表")
    @GetMapping("/fcyh/getMsg")
    public AjaxResult getMsg() {
        Map<String, Object> map = fangChanYaoHaoService.getMsg(fgjUrl, fgwUrl, date, status);
        return AjaxResult.success(map);
    }

    @ApiOperation(value = "根据项目名称获取发改委均价、详情", notes = "解决部分房产局和发改委项目名不匹配问题")
    @GetMapping("/fcyh/getFgwByProjectName")
    public AjaxResult getFgwByProjectName(@RequestParam @ApiParam(name = "txtspname", value = "项目名称", required = true) String txtspname) {
        Map<String, Object> map = fangChanYaoHaoService.getFgwByProjectName(fgwUrl, txtspname);
        return AjaxResult.success(map);
    }
}
