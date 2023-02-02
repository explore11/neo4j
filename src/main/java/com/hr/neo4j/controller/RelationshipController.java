package com.hr.neo4j.controller;

import com.hr.neo4j.service.RelationshipService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/relationship")
@Api(tags = "关系")
public class RelationshipController {

    @Resource
    private RelationshipService relationshipService;
}
