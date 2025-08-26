package com.xuexi.dailytool.utils;

import lombok.Data;

import java.util.Date;
@Data
public class SecurityLog {
    // 成员变量
    private String sourceIp;   // 源 IP 地址
    private int count;         // 访问次数
    private Date timestamp;    // 首次出现的时间戳
}