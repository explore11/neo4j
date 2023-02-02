package com.hr.neo4j.base;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 关键事件信息
 */
@Data
@TableName(value = "event_info_table")
public class EventInfoTable extends BasicPage {
    /**
     * 主键
     */
//    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @TableId
    private String id;
    /**
     * 国内/国外
     */
    @ExcelProperty(value = "国内/国外", index = 0)
    @TableField(value = "address")
    private String address;
    /**
     * 核电/QT
     */
    @ExcelProperty(value = "核电/QT", index = 1)
    @TableField(value = "nuclear_power")
    private String nuclearPower;
    /**
     * 日期
     */
    @ExcelProperty(value = "日期", index = 2)
    @TableField(value = "event_date")
    private String eventDate;
    /**
     * 事件编码
     */
    @ExcelProperty(value = "事件编码", index = 3)
    @TableField(value = "event_code")
    private String eventCode;
    /**
     * 事件级别
     */
    @ExcelProperty(value = "事件级别", index = 4)
    @TableField(value = "event_level")
    private String eventLevel;
    /**
     * 事件简述
     */
    @ExcelProperty(value = "事件简述", index = 5)
    @TableField(value = "event_desc")
    private String eventDesc;
    /**
     * 电厂机组（所属基地和型号）
     */
    @ExcelProperty(value = "电厂机组（所属基地和型号）", index = 6)
    @TableField(value = "power_plant_unit")
    private String powerPlantUnit;
    /**
     * 堆型
     */
    @ExcelProperty(value = "堆型", index = 7)
    @TableField(value = "stack_type")
    private String stackType;
    /**
     * 电厂活动
     */
    @ExcelProperty(value = "电厂活动", index = 8)
    @TableField(value = "power_plant_activity")
    private String powerPlantActivity;
    /**
     * 运行状态
     */
    @ExcelProperty(value = "运行状态", index = 9)
    @TableField(value = "run_status")
    private String runStatus;
    /**
     * 班组
     */
    @ExcelProperty(value = "班组", index = 10)
    @TableField(value = "team")
    private String team;
    /**
     * 功率（%）
     */
    @ExcelProperty(value = "功率", index = 11)
    @TableField(value = "rate")
    private String rate;
    /**
     * 直接原因
     */
    @ExcelProperty(value = "直接原因", index = 12)
    @TableField(value = "direct_cause")
    private String directCause;
    /**
     * 根本原因
     */
    @ExcelProperty(value = "根本原因", index = 13)
    @TableField(value = "root_cause")
    private String rootCause;
    /**
     * 涉及的系统
     */
    @ExcelProperty(value = "涉及的系统", index = 14)
    @TableField(value = "involve_system")
    private String involveSystem;
    /**
     * 涉及的设备
     */
    @ExcelProperty(value = "涉及的设备", index = 15)
    @TableField(value = "involve_facility")
    private String involveFacility;
    /**
     * 受影响的设备
     */
    @ExcelProperty(value = "受影响的设备", index = 16)
    @TableField(value = "affect_facility")
    private String affectFacility;
    /**
     * 事件探测和保护
     */
    @ExcelProperty(value = "事件探测和保护", index = 17)
    @TableField(value = "event_detection_and_protection")
    private String eventDetectionAndProtection;
    /**
     * 事件后果
     */
    @ExcelProperty(value = "事件后果", index = 18)
    @TableField(value = "event_consequence")
    private String eventConsequence;
    /**
     * 处置措施
     */
    @ExcelProperty(value = "处置措施", index = 19)
    @TableField(value = "process_measure")
    private String processMeasure;
    /**
     * 关键敏感设备
     */
    @ExcelProperty(value = "关键敏感设备", index = 20)
    @TableField(value = "critical_sensitive_equipment")
    private String criticalSensitiveEquipment;
    /**
     * 摘要(中文)
     */
    @ExcelProperty(value = "摘要(中文)", index = 21)
    @TableField(value = "digest_by_ch")
    private String digestByCh;
    /**
     * 摘要(外文)
     */
    @ExcelProperty(value = "摘要(外文)", index = 22)
    @TableField(value = "digest_by_en")
    private String digestByEn;

    /**
     * 是否删除 0为false 1为true
     */
    @TableLogic
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

}
