package com.xuexi.dailytool;

import com.xuexi.dailytool.FileDataRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDataRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileDataRule record);

    int insertSelective(FileDataRule record);

    FileDataRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileDataRule record);

    int updateByPrimaryKey(FileDataRule record);
}