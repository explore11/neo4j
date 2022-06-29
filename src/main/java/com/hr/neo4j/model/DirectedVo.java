package com.hr.neo4j.model;

import lombok.Data;

@Data
public class DirectedVo {
    private Long personId;
    private Long movieId;
    // 连接类型
    // type == 1  person  -> movie
    // type == 2  movie   -> person
    private Integer type;
    //关系描述
    private String lable;
}
