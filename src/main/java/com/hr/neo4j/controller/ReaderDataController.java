package com.hr.neo4j.controller;

import com.hr.neo4j.service.ReaderDataService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/readerData")
@Api(tags = "读取数据测试")
public class ReaderDataController {

    @Resource
    private ReaderDataService readerDataService;

    /**
     * 导入数据
     *
     * @param file
     */
    @ApiOperation("导入数据")
    @PostMapping("/ImportData")
    public Result ImportData(@RequestParam("file") MultipartFile file) throws IOException {
        readerDataService.ImportData(file);
        return Result.success(Boolean.TRUE);
    }

}
