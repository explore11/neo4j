package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.DirectedRepository;
import com.hr.neo4j.dao.MovieRepository;
import com.hr.neo4j.dao.PersonRepository;
import com.hr.neo4j.model.Directed;
import com.hr.neo4j.model.Movie;
import com.hr.neo4j.model.Person;
import com.hr.neo4j.service.DirectedService;
import com.hr.neo4j.service.MovieService;
import com.hr.neo4j.service.PersonService;
import com.hr.neo4j.service.TestService;
import com.hr.neo4j.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource
    private PersonService personService;
    @Resource
    private MovieService movieService;
    @Resource
    private DirectedService directedService;


    @Resource
    private DirectedRepository directedRepository;
    @Resource
    private PersonRepository personRepository;
    @Resource
    private MovieRepository movieRepository;

    private Map<Integer, Person> personMap = new HashMap<>();
    private Map<Integer, Movie> movieMap = new HashMap<>();


    @Override
    public void addTestData(Integer num) {

        // 添加 person 节点
        addBatchPerson(num);
        // 添加 Movie 节点
        addBatchMovie(num);
        // 添加 person -> Movie 节点
        addBatchPeopleToMovieRelation(num);

    }

    // 添加 movie 节点
    private void addBatchPeopleToMovieRelation(Integer num) {
        List<Directed> directedList = new ArrayList<>();
        Random random = new Random();
        for (Integer i = 0; i < num; i++) {
            Directed directed = new Directed();
            directed.setLabel("参演");

            int nextPersonInt = random.nextInt(num);
            directed.setStartNode(personMap.get(nextPersonInt));

            int nextMovieInt = random.nextInt(num);
            directed.setEndNode(movieMap.get(nextMovieInt));

            directedList.add(directed);
        }

        long start = System.currentTimeMillis();
        log.info("开始添加 person -> Movie 关系，开始时间是 {} " + start);
        directedRepository.saveAll(directedList);
        long end = System.currentTimeMillis();
        log.info("添加person -> Movie  关系结束，结束时间是 {} ，共用时 {} ", end, end - start);
    }


    // 添加 movie 节点
    private void addBatchMovie(Integer num) {
        List<Movie> movieList = new ArrayList<>();
        for (Integer i = 0; i < num; i++) {
            Movie movie = new Movie();
            movie.setType(Constant.MOVIE_TYPE);
            movie.setSize(30);
            movie.setTitle("电影" + i);
            movie.setReleased(i);
            movieList.add(movie);
        }
        long start = System.currentTimeMillis();
        log.info("开始添加 movie 节点，开始时间是 {} " + start);
        Iterable<Movie> movies = movieRepository.saveAll(movieList);
        long end = System.currentTimeMillis();
        log.info("添加 movie 节点结束，结束时间是 {} ，共用时 {} ", end, end - start);

        int i = 0;
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie next = iterator.next();
            movieMap.put(i++, next);
        }
    }


    // 添加 person 节点
    private void addBatchPerson(Integer num) {
        List<Person> personList = new ArrayList<>();
        for (Integer i = 0; i < num; i++) {
            Person person = new Person();
            person.setType(Constant.PERSON_TYPE);
            person.setSize(30);
            person.setTitle("人物" + i);
            person.setBorn(i);
            personList.add(person);
        }
        long start = System.currentTimeMillis();
        log.info("开始添加People节点，开始时间是 {} " + start);
        Iterable<Person> peoples = personRepository.saveAll(personList);
        long end = System.currentTimeMillis();
        log.info("添加People节点结束，结束时间是 {} ，共用时 {} ", end, end - start);

        int i = 0;
        Iterator<Person> iterator = peoples.iterator();
        while (iterator.hasNext()) {
            Person next = iterator.next();
            personMap.put(i++, next);
        }

    }
}
