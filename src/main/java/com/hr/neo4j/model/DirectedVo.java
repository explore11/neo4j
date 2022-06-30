package com.hr.neo4j.model;

import lombok.Data;

@Data
public class DirectedVo {
    private Long personId;
    private Long movieId;
    //关系描述
    private String lable;
}
