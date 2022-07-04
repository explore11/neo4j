package com.hr.neo4j.controller;

import com.hr.neo4j.service.TestService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Api(tags = "添加测试数据")
public class TestController {


    @Resource
    private TestService testService;

    @GetMapping("/addTest")
    private Result addTest(Integer num) {
        testService.addTestData(num);

        return Result.success(Boolean.TRUE);
    }


}
