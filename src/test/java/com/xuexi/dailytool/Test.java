package com.xuexi.dailytool;

import com.xuexi.dailytool.utils.*;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 1. 初始化图数据库
        GraphClient.initSchema();

        // 2. 导入安全日志数据
        List<SecurityLog> logs = loadSecurityLogs();
        new DataImporter().importIps(logs);

        // 3. 执行合并流程
        new MergeController().startMergeProcess();

        // 4. 导出优化结果
        exportOptimizedPolicies();
    }

    private static List<SecurityLog> loadSecurityLogs() {
        // 实现具体日志加载逻辑
        List<SecurityLog> securityLogs = TestDataGenerator.generateSampleData();
        return securityLogs;
    }

    private static void exportOptimizedPolicies() {
        // 实现策略导出逻辑
    }
}
