package com.hr.neo4j.base;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * 基础关系类型
 */
@Data
public class BaseRel {
    @Id
    @GeneratedValue
    private Long id;
}