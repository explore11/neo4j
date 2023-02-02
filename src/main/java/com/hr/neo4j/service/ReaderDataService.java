package com.hr.neo4j.service;

import com.hr.neo4j.base.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReaderDataService {
    /**
     * 导入数据
     *
     * @param file
     */
    Boolean ImportData(MultipartFile file) throws IOException;

    /**
     * 解析数据
     */
    void parseData(List<FileData> fileDataList);

    /**
     * 删除所有关系、节点数据
     *
     * @return
     */
    Boolean delAllData();



}
