package com.hr.neo4j.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hr.neo4j.base.FileData;
import com.hr.neo4j.service.ReaderDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//拦截器
@Component
@Slf4j
public class EasyExcelListener extends AnalysisEventListener<FileData> {

    // 使用静态注入
    private static ReaderDataService readerDataService;
    //设置最大数
    private static final int BATCH_COUNT = 100;

    //读取数据
    private List<FileData> fileDataList = new ArrayList<>();

    public EasyExcelListener() {
    }

    //注入service
    @Autowired
    public void setElementInfoService(ReaderDataService elementInfoService) {
        EasyExcelListener.readerDataService = elementInfoService;
    }


    //每一次解析
    @Override
    public void invoke(FileData data, AnalysisContext context) {
        fileDataList.add(data);
    }


    //解析完毕需要做的功能 这里是解析完毕存入数据库 清空当前List
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("数据读取完毕");
        readerDataService.parseData(fileDataList);
    }

    //解析表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        for (Integer integer : headMap.keySet()) {
            System.out.println("表头：" + headMap.get(integer));
        }
    }

    //处理异常
    @Override
    public void onException(Exception exception, AnalysisContext context) {

    }
}