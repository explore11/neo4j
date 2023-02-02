package com.hr.neo4j.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.neo4j.base.BasicPage;
import com.hr.neo4j.base.EventInfoTable;
import com.hr.neo4j.dao.EventInfoTableMapper;
import com.hr.neo4j.listener.EventInfoListener;
import com.hr.neo4j.service.IEventInfoTableService;
import com.hr.neo4j.util.DataCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventInfoServiceImpl extends ServiceImpl<EventInfoTableMapper, EventInfoTable> implements IEventInfoTableService {

    @Resource
    private EventInfoTableMapper eventInfoTableMapper;

    /**
     * 导入关键事件信息数据
     *
     * @param file
     * @return
     */
    @Override
    public Boolean ImportEventInfoData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), EventInfoTable.class, new EventInfoListener()).sheet().headRowNumber(1).doRead();
        return Boolean.TRUE;
    }

    /**
     * 查询
     */
    @Override
    public EventInfoTable selectEventInfoTableById(String id) {
        return eventInfoTableMapper.selectById(id);
    }

    /**
     * 查询列表
     */
    @Override
    public IPage<EventInfoTable> selectEventInfoTableList(BasicPage basicPage) {
        Integer page = basicPage.getPage();
        Integer total = basicPage.getTotal();
        Page<EventInfoTable> eventInfoTablePage = new Page<>(page, total);
        IPage<EventInfoTable> iPage = eventInfoTableMapper.selectPage(eventInfoTablePage, new QueryWrapper<>());
        return iPage;
    }

    /**
     * 新增
     */
    @Override
    public int insertEventInfoTable(EventInfoTable eventInfoTable) {
        return eventInfoTableMapper.insert(eventInfoTable);
    }

    /**
     * 修改
     */
    @Override
    public int updateEventInfoTable(EventInfoTable eventInfoTable) {
        return eventInfoTableMapper.updateById(eventInfoTable);
    }

    /**
     * 批量删除
     */
    @Override
    public int deleteEventInfoTableByIds(List<String> ids) {
        return eventInfoTableMapper.deleteBatchIds(ids);
    }

    /**
     * 批量添加
     */
    @Override
    public int batchSaveData(List<EventInfoTable> eventInfoTableList) {
        return eventInfoTableMapper.batchSaveData(eventInfoTableList);
    }

    /**
     * 批量保存数据
     *
     * @param eventInfoTableList
     */
    @Transactional
    @Override
    public void saveData(List<EventInfoTable> eventInfoTableList) {
        List<EventInfoTable> finalEventInfoDataList = new ArrayList<>();
        //清理编码为空的数据
        for (EventInfoTable eventInfoTable : eventInfoTableList) {
            String eventCode = eventInfoTable.getEventCode();
            //过滤出数据不全为空且存在时间编码的数据
            if (!DataCheckUtils.checkObjAllFieldsIsNull(eventInfoTable) && StringUtils.isNotBlank(eventCode)) {
                finalEventInfoDataList.add(eventInfoTable);
            }
        }
        //批量保存数据
        eventInfoTableMapper.batchSaveData(finalEventInfoDataList);
    }
}
