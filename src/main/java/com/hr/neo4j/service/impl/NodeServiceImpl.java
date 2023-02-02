package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.NodeRepository;
import com.hr.neo4j.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class NodeServiceImpl implements NodeService {

    @Resource
    private NodeRepository nodeRepository;
}
