package com.hr.neo4j.dao;

import com.hr.neo4j.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

//    @Query("MATCH (n:Person) where n.name =~ ('.*'+{2}+'.*') RETURN n ORDER BY n.id DESC SKIP {0}  LIMIT {1}")
//    List<Person> getPersonByName(Integer page, Integer size, String name);

    @Query("MATCH (n:Person) RETURN n")
    List<Person> selectAllPerson();


}

