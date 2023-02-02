package com.hr.neo4j.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hr.neo4j.base.BasicPage;
import com.hr.neo4j.base.EventInfoTable;
import com.hr.neo4j.listener.EventInfoListener;
import com.hr.neo4j.service.IEventInfoTableService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/eventInfo")
@Api(tags = "关键事件信息")
public class EventInfoController {

    @Resource
    private IEventInfoTableService eventInfoTableService;

    /**
     * 导入关键事件信息数据
     *
     * @param file
     */
    @ApiOperation("导入数据")
    @PostMapping("/ImportEventInfoData")
    public Result ImportEventInfoData(@RequestParam("file") MultipartFile file) throws IOException {
        Boolean flag = eventInfoTableService.ImportEventInfoData(file);
        return Result.success(flag);
    }

    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") String id) {
        return Result.success(eventInfoTableService.selectEventInfoTableById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public Result add(@RequestBody EventInfoTable eventInfoTable) {
        return Result.success(eventInfoTableService.insertEventInfoTable(eventInfoTable));
    }

    /**
     * 修改
     */
    @PutMapping
    public Result edit(@RequestBody EventInfoTable eventInfoTable) {
        return Result.success(eventInfoTableService.updateEventInfoTable(eventInfoTable));
    }

    /**
     * 删除
     */
    @DeleteMapping("/deleteByIds")
    public Result remove(@RequestBody List<String> ids) {
        return Result.success(eventInfoTableService.deleteEventInfoTableByIds(ids));
    }


    /**
     * 查询列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody BasicPage basicPage) {
        IPage<EventInfoTable> eventInfoTableIPage = eventInfoTableService.selectEventInfoTableList(basicPage);
        return Result.success(eventInfoTableIPage);
    }

}
