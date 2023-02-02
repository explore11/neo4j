package com.hr.neo4j.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.neo4j.base.EventInfoTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventInfoTableMapper extends BaseMapper<EventInfoTable> {
    int batchSaveData(@Param("eventInfoTableList") List<EventInfoTable> eventInfoTableList);
}
