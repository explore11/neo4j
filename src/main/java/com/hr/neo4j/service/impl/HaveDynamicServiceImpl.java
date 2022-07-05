package com.hr.neo4j.service.impl;

import com.hr.neo4j.dao.HaveDynamicRepository;
import com.hr.neo4j.model.Car;
import com.hr.neo4j.model.Customer;
import com.hr.neo4j.model.HaveDynamic;
import com.hr.neo4j.service.HaveDynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HaveDynamicServiceImpl implements HaveDynamicService {

    @Resource
    private HaveDynamicRepository haveDynamicRepository;

    @Override
    public Boolean addHaveDynamic() {
        List<HaveDynamic> haveDynamicList = new ArrayList<>();

        Customer customer = new Customer();
        customer.setName("John");
        Car car = new Car();
        car.setBrand("Benz");

        // customer -> car
        HaveDynamic<Customer, Car> haveDynamic = new HaveDynamic<>();
        haveDynamic.setCreateTime(LocalDateTime.now().toString());
        haveDynamic.setStartNode(customer);
        haveDynamic.setEndNode(car);

        haveDynamicList.add(haveDynamic);


        // car -> customer
        HaveDynamic<Car, Customer> haveDynamic1 = new HaveDynamic<>();
        haveDynamic1.setCreateTime(LocalDateTime.now().toString());
        haveDynamic1.setStartNode(car);
        haveDynamic1.setEndNode(customer);

        haveDynamicList.add(haveDynamic1);

        haveDynamicRepository.saveAll(haveDynamicList);

        return null;
    }
}
