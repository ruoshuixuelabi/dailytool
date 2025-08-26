package com.xuexi.dailytool;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("原始值")
    private String string;
    @ExcelProperty("条件")
    private String condition;
    @ExcelProperty("另外的值")
    private String string2;
    @ExcelProperty("索引")
    private String index1;
    @ExcelProperty("索引2")
    private String index2;
    @ExcelProperty("条件2")
    private String condition2;
}