package com.xuexi.dailytool.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataGenerator {
    public static List<SecurityLog> generateSampleData() {
        List<SecurityLog> logs = new ArrayList<>();
        // 生成连续IP地址
        for (int i = 0; i < 100; i++) {
            SecurityLog log = new SecurityLog();
            log.setSourceIp("192.168.1." + i);
            log.setCount(10);
            log.setTimestamp(new Date());
            logs.add(log);
        }
        return logs;
    }
}