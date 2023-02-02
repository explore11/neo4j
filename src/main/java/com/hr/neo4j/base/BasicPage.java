package com.hr.neo4j.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicPage {
    @ApiModelProperty(value = "当前页")
    @TableField(exist = false)
    private Integer page;

    @ApiModelProperty(value = "每页总数")
    @TableField(exist = false)
    private Integer total;
}