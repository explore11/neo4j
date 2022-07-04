package com.hr.neo4j.controller;

import com.hr.neo4j.model.DirectedVo;
import com.hr.neo4j.service.DirectedService;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("movie")
@Api(tags = "Directed测试")
public class DirectController {
    @Resource
    private DirectedService directedService;

    // 创建节点并添加关系
    //match(m:Movie) where id(m) = 44
    //create(p:Person{name:"小四",age:23,type:"Person",size:30,born:10})
    //create (p)-[r:directed]->(m) return r


    @PostMapping("/addDirected")
    @ApiOperation(value = " 添加关系  导演(人与电影的关系) 人 → 电影")
    public Result addDirected(@RequestBody DirectedVo directedVo) {
        return Result.success(directedService.addDirected(directedVo));
    }

    @PostMapping("/updateDirectedById")
    @ApiOperation(value = " 根据id修改关系")
    public Result updateDirected(@RequestBody DirectedVo directedVo) {
        return Result.success(directedService.updateDirected(directedVo));
    }


    @GetMapping("/delDirected")
    @ApiOperation(value = "根据person的属性名和电影标题删除关系")
    public Result delDirected(String personName, String movieTitle, Integer type) {
        return Result.success(directedService.delDirected(personName, movieTitle, type));
    }

    @GetMapping("/delDirectedById")
    @ApiOperation(value = "根据Id删除关系")
    public Result delDirectedById(Long relationId) {
        return Result.success(directedService.delDirectedById(relationId));
    }

    @GetMapping("/delAllDirected")
    @ApiOperation(value = "删除所有person和电影的关系")
    public Result delAllDirected() {
        return Result.success(directedService.delAllDirected());
    }


    @GetMapping("/selectAll")
    @ApiOperation(value = "查询所有的person节点、电影节点、以及person和movie的关系")
    public Result selectAll() {
        return Result.success(directedService.selectAll());
    }

    @GetMapping("/selectAllRelation")
    @ApiOperation(value = "查询所有的person和movie的关系")
    public Result selectAllRelation() {
        return Result.success(directedService.selectAllRelation());
    }

    @GetMapping("/delNodeAndRelationByNodeId/{nodeId}")
    @ApiOperation(value = "根据节点Id删除该节点以及所有的关系")
    public Result delNodeAndRelationByNodeId(@PathVariable("nodeId") Long nodeId) {
        return Result.success(directedService.delNodeAndRelationByNodeId(nodeId));
    }


    @GetMapping("/getSourceAndTargetAllRelationBySourceNodeId/{sourceNodeId}")
    @ApiOperation(value = "查看两个节点（源节点和目标节点）中的所有的关系")
    public Result getSourceAndTargetAllRelationBySourceNodeId(@PathVariable("sourceNodeId") Long sourceNodeId) {
        return Result.success(directedService.getSourceAndTargetAllRelationBySourceNodeId(sourceNodeId));
    }


    @PostMapping("/addRelationList")
    @ApiOperation(value = "批量添加关系")
    public Result addRelationList(@RequestBody List<DirectedVo> directedVoList) {
        return Result.success(directedService.addRelationList(directedVoList));
    }


}
