package com.hr.neo4j.service;

import com.hr.neo4j.model.Person;
import com.hr.neo4j.util.RequestVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonService {
    Person addPerson(Person person);

    Person findOnePerson(long id);

    void deleteOnePerson(long id);

    Page<Person> findPersonList(RequestVo pageRequest);

    List<Person> selectAllPerson();

    Person updatePerson(Person person);
}
