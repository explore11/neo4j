package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.DirectedRepository;
import com.hr.neo4j.dao.MovieRepository;
import com.hr.neo4j.model.Movie;
import com.hr.neo4j.service.MovieService;
import com.hr.neo4j.service.PersonService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Resource
    private MovieRepository movieRepository;

    @Override
    public List<Movie> selectAllMovie() {
        return movieRepository.selectAllMovie();
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findOneMovie(long id) {
        return movieRepository.findById(id).get();
    }

    @Override
    public void deleteOneMovie(long id) {
        movieRepository.deleteById(id);
    }


}
