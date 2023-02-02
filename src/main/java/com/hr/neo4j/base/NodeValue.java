package com.hr.neo4j.base;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NodeValue {
    // 标签类型集合
    private Iterable<String> labels;
    // 节点id
    private Long id;
    // 属性集合
    private Map<String, Object> properties;
}
