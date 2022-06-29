package com.hr.neo4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "directed")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectedNodeReverse {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "lable")
    private String lable;

    @StartNode
    private Movie startNode;

    @EndNode
    private Person endNode;


    // 前端需要 startNode 中的Id
    private String source;
    // 前端需要 endNode 中的Id
    private String target;


}
