package com.hr.neo4j.dao;

import com.hr.neo4j.model.Directed;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectedRepository extends Neo4jRepository<Directed, Long> {

    @Query("match (p:Person{name:$personName})-[r:directed]->(m:Movie{title:$movieTitle}) delete r")
    Boolean delDirected(String personName, String movieTitle);

    @Query("match (m:Movie{title:$movieTitle})-[r:directed]->(p:Person{name:$personName}) delete r")
    Boolean delDirectedReverse(String movieTitle, String personName);

    @Query("match (p:Person)-[r:directed]->(m:Movie) delete r")
    void delAllDirected();

    @Query("MATCH p=()-[r:directed]->() RETURN p")
    List<Directed> selectAllRelation();

    @Query("MATCH (n) WHERE id(n) = $nodeId DETACH delete n")
    void delNodeAndRelationByNodeId(Long nodeId);

    @Query("MATCH (p:Person)-[r:directed]-(m:Movie) where id(r)= $relationId delete r")
    void delDirectedById(Long relationId);

    @Query("MATCH p=(n)-[r:directed]->(m) where id(n)=$sourceNodeId RETURN p")
    List<Directed> getSourceAndTargetAllRelationBySourceNodeId(Long sourceNodeId);
}
