package com.hr.neo4j.controller;

import com.hr.neo4j.service.NodeService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/node")
@Api(tags = "节点")
public class NodeController {

    @Resource
    private NodeService nodeService;

    /**
     * 根据名称查找节点信息
     */
    @GetMapping("/findNodeInfoByName/{name}")
    @ApiOperation(value = "根据名称查找节点信息")
    public Result findNodeInfoByName(@PathVariable("name") String name) {
        return Result.success(nodeService.findNodeInfoByName(name));
    }


}
