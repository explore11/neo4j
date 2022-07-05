package com.hr.neo4j.dao;

import com.hr.neo4j.model.HaveDynamic;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface HaveDynamicRepository extends Neo4jRepository<HaveDynamic, Long> {
}
