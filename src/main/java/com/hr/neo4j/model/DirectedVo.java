package com.hr.neo4j.model;

import lombok.Data;

@Data
public class DirectedVo {
    private Long id;
    private Long personId;
    private Long movieId;
    //关系描述
    private String label;
    private Integer type;

    private String reverseLable;
}
