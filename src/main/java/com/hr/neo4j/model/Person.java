package com.hr.neo4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "Person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String title;

    @Property(name = "born")
    private int born;

    @Property(name = "size")
    private int size;

    @Property(name = "type")
    private String type;

}