package com.hr.neo4j.dao;

import com.hr.neo4j.base.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {
}
