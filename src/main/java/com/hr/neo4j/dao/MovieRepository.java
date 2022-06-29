package com.hr.neo4j.dao;

import com.hr.neo4j.model.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    @Query("MATCH (n:Movie) RETURN n")
    List<Movie> selectAllMovie();

}
