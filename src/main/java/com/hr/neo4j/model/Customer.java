package com.hr.neo4j.model;

import com.hr.neo4j.base.BaseNode;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Customer extends BaseNode {
    private String name;
}
