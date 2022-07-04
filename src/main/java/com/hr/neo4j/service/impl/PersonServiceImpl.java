package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.PersonRepository;
import com.hr.neo4j.model.Person;
import com.hr.neo4j.service.PersonService;
import com.hr.neo4j.util.Constant;
import com.hr.neo4j.util.RequestVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepository personRepository;


    @Override
    public Person updatePerson(Person person) {
        Long personId = person.getId();
        Integer size = person.getSize();
        String title = person.getTitle();
        Integer born = person.getBorn();
        return personRepository.updatePerson(personId,size,title,born);
    }

    @Override
    public List<Person> selectAllPerson() {
        return personRepository.selectAllPerson();
    }

    @Override
    public Page<Person> findPersonList(RequestVo requestVo) {
//        PageRequest pageRequest = PageRequest.of( requestVo.page, requestVo.size, sort);

        PageRequest pageRequest = PageRequest.of(requestVo.page, requestVo.size);
        Page<Person> page = personRepository.findAll(pageRequest);
        return page;
    }


    @Override
    public Person addPerson(Person person) {
        person.setType(Constant.PERSON_TYPE);
        return personRepository.save(person);
    }

    @Override
    public Person findOnePerson(long id) {
        return personRepository.findById(id).get();
    }

    @Override
    public void deleteOnePerson(long id) {
        personRepository.deleteById(id);
    }

}
