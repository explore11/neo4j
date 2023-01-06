package com.hr.neo4j.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件数据
 */
@Data
public class FileData implements Serializable {
    /**
     * 事件编码
     */
    @ExcelProperty(value = "事件编码", index = 3)
    private String eventCode;

    /**
     * 事件级别
     */
    @ExcelProperty(value = "事件级别", index = 4)
    private String eventLevel;


    /**
     * 电厂机组
     */
    @ExcelProperty(value = "电厂机组", index = 6)
    private String powerPlantUnit;

    /**
     * 堆型
     */
    @ExcelProperty(value = "堆型", index = 7)
    private String stackType;


    /**
     * 直接原因
     */
    @ExcelProperty(value = "直接原因", index = 12)
    private String directCause;


    /**
     * 根本原因
     */
    @ExcelProperty(value = "根本原因", index = 13)
    private String rootCause;


    /**
     * 涉及的系统
     */
    @ExcelProperty(value = "涉及的系统", index = 14)
    private String involveSystem;


    /**
     * 涉及的设备
     */
    @ExcelProperty(value = "涉及的设备", index = 15)
    private String involveFacility;

    /**
     * 受影响的设备
     */
    @ExcelProperty(value = "受影响的设备", index = 16)
    private String affectFacility;



    /**
     * 事件探测和保护
     */
    @ExcelProperty(value = "事件探测和保护", index = 17)
    private String eventDetectionAndProtection;


    /**
     * 事件后果
     */
    @ExcelProperty(value = "事件后果", index = 18)
    private String eventConsequence;


    /**
     * 处置措施
     */
    @ExcelProperty(value = "处置措施", index = 19)
    private String processMeasure;



}
