package com.hr.neo4j.service;

import com.hr.neo4j.model.Movie;
import com.hr.neo4j.util.RequestVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    Movie addMovie(Movie movie);

    Movie findOneMovie(long id);

    void deleteOneMovie(long parseLong);

    List<Movie> selectAllMovie();

    Page<Movie> findMovieList(RequestVo pageRequest);

    Movie updateMovie(Movie movie);
}
