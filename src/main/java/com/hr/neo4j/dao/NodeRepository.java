package com.hr.neo4j.dao;

import com.hr.neo4j.base.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {
}
