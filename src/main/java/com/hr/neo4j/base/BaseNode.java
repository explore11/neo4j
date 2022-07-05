package com.hr.neo4j.base;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * 基础节点类型
 */
@Data
public class BaseNode {
    @Id
    @GeneratedValue
    private Long id;
}