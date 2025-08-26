package com.xuexi.dailytool;

import com.xuexi.dailytool.AssetDataLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetDataLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssetDataLevel record);

    int insertSelective(AssetDataLevel record);

    AssetDataLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AssetDataLevel record);

    int updateByPrimaryKey(AssetDataLevel record);
}