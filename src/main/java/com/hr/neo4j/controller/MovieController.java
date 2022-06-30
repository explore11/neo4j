package com.hr.neo4j.controller;

import com.hr.neo4j.model.Movie;
import com.hr.neo4j.service.MovieService;
import com.hr.neo4j.util.RequestVo;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie")
@Api(tags = "Movie测试")
public class MovieController {
    @Resource
    private MovieService movieService;

    @PostMapping("addMovie")
    @ApiOperation(value = "添加电影")
    public Result addMovie(@RequestBody Movie movie) {
        return Result.success(movieService.addMovie(movie));
    }

    @GetMapping("/findOneMovie/{id}")
    @ApiOperation(value = "根据id查找电影")
    public Result findOneMovie(@PathVariable("id") String id) {
        return Result.success(movieService.findOneMovie(Long.parseLong(id)));
    }

    @GetMapping("/deleteOneMovie/{id}")
    @ApiOperation(value = "根据ID删除电影")
    public Result deleteOneMovie(@PathVariable("id") String id) {
        movieService.deleteOneMovie(Long.parseLong(id));
        return Result.success(Boolean.TRUE);
    }

    @PostMapping("/findMovieList")
    @ApiOperation(value = "查询电影列表")
    public Result findMovieList(@RequestBody RequestVo pageRequest) {
        return Result.success(movieService.findMovieList(pageRequest));
    }


}
