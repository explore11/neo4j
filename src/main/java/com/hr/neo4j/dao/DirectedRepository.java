package com.hr.neo4j.dao;

import com.hr.neo4j.model.Directed;
import com.hr.neo4j.model.Movie;
import com.hr.neo4j.model.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectedRepository extends Neo4jRepository<Directed, Long> {

    @Query("match (p:Person{name:$personName})-[r:directed]->(m:Movie{title:$movieTitle}) delete r")
    Boolean delDirected(String personName, String movieTitle);

//    @Query("")
//    Directed saveRelationReverse(Long personId, Long movieId);

    @Query("match (m:Movie{title:$movieTitle})-[r:directed]->(p:Person{name:$personName}) delete r")
    Boolean delDirectedReverse(String movieTitle, String personName);

    @Query("match (p:Person)-[r:directed]->(m:Movie) delete r")
    void delAllDirected();

    @Query("MATCH p=()-[r:directed]->() RETURN p")
    List<Directed> selectAllRelation();

    @Query("MATCH (r)\n" +
            "WHERE id(r) = $nodeId\n" +
            "DETACH DELETE r")
    void delNodeAndRelationByNodeId(Long nodeId);

    @Query("MATCH (p:Person)-[r:directed]-(m:Movie) where id(r)= $relationId delete r")
    void delDirectedById(Long relationId);

    @Query("MATCH p=(n)-[r:directed]->(m) where id(n)=$sourceNodeId RETURN p")
    List<Directed> getSourceAndTargetAllRelationBySourceNodeId(Long sourceNodeId);

    @Query("MATCH p=(n)-[r:directed]->(m) where id(n)=$sourceNodeId RETURN n")
    List<Person> getSourceNodeBySourceNodeId(Long sourceNodeId);

    @Query("MATCH p=(n)-[r:directed]->(m) where id(n)=$sourceNodeId RETURN m")
    List<Movie> getTargetNodeBySourceNodeId(Long sourceNodeId);

    @Query("match (p:Person) where id(p)=$personId match (m:Movie) where id(m)=$movieId create (m)-[r:directed{lable:$label}]->(p) return r")
    Directed saveRelationReverse(Long personId, Long movieId, String label);

    @Query("match (p:Person) where id(p)=$personId match (m:Movie) where id(m)=$movieId create(p)-[pm:directed{lable:$label}]->(m) create (m)-[mp:directed{lable:$reverseLabel}]->(p) return st,ts")
    List<Directed> saveRelation(Long personId, Long movieId, String label, String reverseLabel);

    @Query("MATCH ()-[r:directed]->() where id(r)=33 set r.lable=$label RETURN r")
    Directed updateDirected(Long directedId, String label);
}
