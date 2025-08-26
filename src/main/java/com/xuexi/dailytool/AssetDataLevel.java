package com.xuexi.dailytool;

import lombok.Data;

/**
 * 分级表
 */
@Data
public class AssetDataLevel {
    /**
    * 主键id
    */
    private Integer id;

    /**
    * 描述
    */
    private String remark;

    /**
    * 模板ID
    */
    private Integer templateId;

    /**
    * 分级名称
    */
    private String levelName;
}