package com.xuexi.dailytool.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.gremlin.Result;
import org.apache.hugegraph.structure.gremlin.ResultSet;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class MergeController {

    public void startMergeProcess() {
        try {
            new SubnetMerger().mergeSubnets();
            generateFinalReport();
        }
        catch (Exception e) {
            log.error("Merge process failed", e);
            rollbackIncompleteMerges();
        }
    }

    private void generateFinalReport() {
        HugeClient client = GraphClient.getClient();
        String query = "g.V().hasLabel('Subnet').valueMap()";
        ResultSet result = client.gremlin().gremlin(query).execute();
        log.info("Final Subnets Report:{}", result.data());
        Iterator<Result> iterator = result.iterator();
        while (iterator.hasNext()) {
            Result item = iterator.next();
            Map<String, Object> props = (Map<String, Object>) item.getObject();
            log.info("Subnet: {}/{} ({} children)",
                    props.get("network"),
                    props.get("mask"),
                    props.get("child_count"));
        }
//        result.stream().forEach(item -> {
//            Map<String, Object> props = (Map<String, Object>) item.getObject();
//            log.info("Subnet: {}/{} ({} children)",
//                    props.get("network"),
//                    props.get("mask"),
//                    props.get("child_count"));
//        });
    }

    private void rollbackIncompleteMerges() {
        // 实现回滚逻辑（可根据需要扩展）
        String query = "g.V().hasLabel('Subnet').drop()";
        GraphClient.getClient().gremlin().gremlin(query).execute();
    }
}