package com.hr.neo4j.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "Relationship")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relationship {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "actualId")
    private String actualId;

    /**
     * 开始节点
     */
    @StartNode
    private Node startNode;

    /**
     * 结束节点
     */
    @EndNode
    private Node endNode;

}
