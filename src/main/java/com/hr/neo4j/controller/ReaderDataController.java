package com.hr.neo4j.controller;

import com.hr.neo4j.service.ReaderDataService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
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
        Boolean flag = readerDataService.ImportData(file);
        return Result.success(flag);
    }

    /**
     * 删除所有关系、节点数据
     */
    @ApiOperation("删除所有关系、节点数据")
    @DeleteMapping("/delAllData")
    public Result delAllData() {
        Boolean flag = readerDataService.delAllData();
        return Result.success(flag);
    }

}
