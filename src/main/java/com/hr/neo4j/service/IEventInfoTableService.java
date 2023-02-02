package com.hr.neo4j.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.neo4j.base.BasicPage;
import com.hr.neo4j.base.EventInfoTable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEventInfoTableService extends IService<EventInfoTable> {
    /**
     * 导入关键事件信息数据
     *
     * @param file
     * @return
     */
    Boolean ImportEventInfoData(MultipartFile file) throws IOException;

    /**
     * 查询
     */
    EventInfoTable selectEventInfoTableById(String id);

    /**
     * 查询列表
     */
    IPage<EventInfoTable> selectEventInfoTableList(BasicPage basicPage);

    /**
     * 新增
     */
    int insertEventInfoTable(EventInfoTable eventInfoTable);

    /**
     * 修改
     */
    int updateEventInfoTable(EventInfoTable eventInfoTable);

    /**
     * 批量删除
     */
    int deleteEventInfoTableByIds(List<String> ids);

    /**
     * 批量添加
     */
     int batchSaveData(List<EventInfoTable> eventInfoTableList);

    /**
     * 保存数据
     * @param eventInfoTableList
     */
    void saveData(List<EventInfoTable> eventInfoTableList);
}
