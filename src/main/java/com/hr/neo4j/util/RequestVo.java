package com.hr.neo4j.util;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class RequestVo {
    //private Sort sort;
    // 从0开始
    public int page;
    public int size;

}
