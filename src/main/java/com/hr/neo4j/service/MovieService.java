package com.hr.neo4j.service;

import com.hr.neo4j.model.Directed;
import com.hr.neo4j.model.Movie;

import java.util.List;

public interface MovieService {
    Movie addMovie(Movie movie);

    Movie findOneMovie(long id);

    void deleteOneMovie(long parseLong);


    List<Movie> selectAllMovie();

}
