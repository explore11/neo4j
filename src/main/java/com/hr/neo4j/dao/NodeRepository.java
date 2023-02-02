package com.hr.neo4j.dao;

import com.hr.neo4j.base.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {
    @Query("MATCH (n:Node{name:$name}) RETURN n ")
    List<Node> findNodeInfoByName(String name);
}
