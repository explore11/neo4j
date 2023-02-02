package com.hr.neo4j.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hr.neo4j.base.EventInfoTable;
import com.hr.neo4j.service.IEventInfoTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EventInfoListener extends AnalysisEventListener<EventInfoTable> {
    // 使用静态注入
    private static IEventInfoTableService eventInfoTableService;

    @Autowired
    public void setEventInfoTableService(IEventInfoTableService iEventInfoTableService) {
        EventInfoListener.eventInfoTableService = iEventInfoTableService;
    }

    //无参构造
    public EventInfoListener() {
    }

    //存储数据
    private List<EventInfoTable> eventInfoTableList = new ArrayList<>();

    //读取数据
    @Override
    public void invoke(EventInfoTable data, AnalysisContext context) {
        eventInfoTableList.add(data);
    }

    ///解析完毕需要做的功能 这里是解析完毕存入数据库 清空当前List
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("数据读取完毕");
        //保存数据
        eventInfoTableService.saveData(eventInfoTableList);
    }

    /**
     * 异常
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.info("出现了异常");
        exception.printStackTrace();
    }
}
