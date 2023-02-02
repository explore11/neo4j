package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.RelationshipRepository;
import com.hr.neo4j.service.RelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RelationshipServiceImpl implements RelationshipService {

    @Resource
    private RelationshipRepository relationshipRepository;

}
