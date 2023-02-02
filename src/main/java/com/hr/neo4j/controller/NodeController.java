package com.hr.neo4j.controller;

import com.hr.neo4j.service.NodeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/node")
@Api(tags = "节点")
public class NodeController {

    @Resource
    private NodeService nodeService;




}
