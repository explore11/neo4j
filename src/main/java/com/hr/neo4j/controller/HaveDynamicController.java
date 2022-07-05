package com.hr.neo4j.controller;

import com.hr.neo4j.model.DirectedVo;
import com.hr.neo4j.service.HaveDynamicService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("HaveDynamic")
@Api(tags = "HaveDynamic测试")
public class HaveDynamicController {

    @Resource
    private HaveDynamicService haveDynamicService;

    @PostMapping("/addHaveDynamic")
    @ApiOperation(value = "测试添加双向添加关系")
    public Result addHaveDynamic() {
        return Result.success(haveDynamicService.addHaveDynamic());
    }
}
