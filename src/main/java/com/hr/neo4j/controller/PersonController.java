package com.hr.neo4j.controller;

import com.hr.neo4j.model.Person;
import com.hr.neo4j.service.PersonService;
import com.hr.neo4j.util.RequestVo;
import com.hr.neo4j.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/person")
@Api(tags = "person 测试")
public class PersonController {
    @Resource
    private PersonService personService;

    @PostMapping("addPerson")
    @ApiOperation(value = "添加人")
    public Result addPerson(@RequestBody Person person) {
        return Result.success(personService.addPerson(person));
    }

    @GetMapping("/findOnePerson/{id}")
    @ApiOperation(value = "根据ID查询")
    public Result findOnePerson(@PathVariable("id") String id) {
        return Result.success(personService.findOnePerson(Long.parseLong(id)));
    }

    @PostMapping("/findPersonList")
    @ApiOperation(value = "查询列表")
    public Result findPersonList(@RequestBody RequestVo pageRequest) {
        Page<Person> personList = personService.findPersonList(pageRequest);
        return Result.success(personList);
    }


    @GetMapping("/deleteOnePerson/{id}")
    @ApiOperation(value = "根据ID删除人")
    public Result deleteOnePerson(@PathVariable("id") String id) {
        personService.deleteOnePerson(Long.parseLong(id));
        return Result.success(Boolean.TRUE);
    }

}
