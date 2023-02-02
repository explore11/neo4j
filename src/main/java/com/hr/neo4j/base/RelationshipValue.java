package com.hr.neo4j.base;

import lombok.Data;

import java.util.Map;

@Data
public class RelationshipValue {
    private Long start;
    private Long end;
    private Long id;
    private String type;
    private Map<String,Object> properties;

}
