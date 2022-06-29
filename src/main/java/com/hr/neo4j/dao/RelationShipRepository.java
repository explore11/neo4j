package com.hr.neo4j.dao;

import com.hr.neo4j.model.RelationShip;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationShipRepository extends Neo4jRepository<RelationShip, Long> {

}
