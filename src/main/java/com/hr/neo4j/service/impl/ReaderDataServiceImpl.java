package com.hr.neo4j.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.hr.neo4j.base.FileData;
import com.hr.neo4j.listener.EasyExcelListener;
import com.hr.neo4j.service.ReaderDataService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderDataServiceImpl implements ReaderDataService {
    /**
     * 导入数据
     *
     * @param file
     */
    @Override
    public void ImportData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), FileData.class, new EasyExcelListener()).sheet().headRowNumber(1).doRead();
    }


    /**
     * 解析数据
     *
     * @param fileDataList
     */
    @Override
    public void parseData(List<FileData> fileDataList) {
        List<FileData> clearAfterDataList = new ArrayList<>();
        //清晰数据 去除全部为空的对象
        for (FileData fileData : fileDataList) {
            if (!checkObjAllFieldsIsNull(fileData)) {
                clearAfterDataList.add(fileData);
            }
        }

        //获取

        System.out.println(clearAfterDataList);
    }

    /**
     * 判断属性中的所有内容为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        // 如果对象为null直接返回true
        if (null == object) {
            return true;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果有一个属性值不为null，且值不是空字符串，就返回false
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
