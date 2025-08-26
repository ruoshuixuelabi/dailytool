package com.xuexi.dailytool;

import com.xuexi.dailytool.AssetDataCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetDataCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssetDataCategory record);

    int insertSelective(AssetDataCategory record);

    AssetDataCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AssetDataCategory record);

    int updateByPrimaryKey(AssetDataCategory record);
}