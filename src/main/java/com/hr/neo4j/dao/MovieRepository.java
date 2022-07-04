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

    @Query("MATCH (m:Movie) where id(m)=$movieId  set m.size=$size,n.title=$title,n.released=$released return m")
    Movie updateMovie(Long movieId,Integer size, String title, Integer released);
}
