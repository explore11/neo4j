package com.hr.neo4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "Movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "title")
    private String title;

    @Property(name = "released")
    private int released;

    @Property(name = "size")
    private int size;

    @Property(name = "type")
    private String type;

}
