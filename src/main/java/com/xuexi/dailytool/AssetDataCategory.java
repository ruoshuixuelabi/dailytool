package com.xuexi.dailytool;

import java.util.Date;
import lombok.Data;

/**
 * 分类表
 */
@Data
public class AssetDataCategory {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 分类名称
    */
    private String categoryName;

    /**
    * 分类编号
    */
    private String categorySign;

    /**
    * 父级id
    */
    private Integer parentId;

    /**
    * 父级分类id
    */
    private String parentSign;

    /**
    * 备注
    */
    private String remark;

    /**
    * 模板ID
    */
    private Integer templateId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date modifyTime;
}