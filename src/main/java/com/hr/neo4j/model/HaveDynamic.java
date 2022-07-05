package com.hr.neo4j.model;

import com.hr.neo4j.base.BaseNode;
import com.hr.neo4j.base.BaseRel;
import com.hr.neo4j.util.RelsType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;


@Data
@RelationshipEntity(type = RelsType.HAVE)
public class HaveDynamic<S extends BaseNode, E extends BaseNode> extends BaseRel {

    @Property
    private String createTime;

    @StartNode
    private S startNode;

    @EndNode
    private E endNode;
}
