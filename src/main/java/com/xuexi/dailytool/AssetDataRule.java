package com.xuexi.dailytool;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 识别规则表
 */
@Data
@TableName("asset_data_rule_3")
public class AssetDataRule {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 连接符
     */
    private String operator;

    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 匹配方式
     */
    @TableField("condition_")
    private String condition;
    /**
     * 属性值
     */
    @TableField("attribute_value")
    private String attributeValue;

    /**
     * 规则组id
     */
    private Integer groupId;

    /**
     * 关联类型 1-模型 2-特征
     */
    private Integer relType;

    /**
     * 关联id
     */
    private Integer relId;


}