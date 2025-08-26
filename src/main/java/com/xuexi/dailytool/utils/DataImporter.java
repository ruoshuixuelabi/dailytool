package com.xuexi.dailytool.utils;

import org.apache.hugegraph.driver.GraphManager;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImporter {

    public void importIps(List<SecurityLog> logs) {
        HugeClient client = GraphClient.getClient();
        GraphManager graph = client.graph();
        List<Vertex> vertices = new ArrayList<>();
        for (SecurityLog log : logs) {
            Vertex properties = new Vertex("IP");
//            Map<String, Object> properties = new HashMap<>();
            properties.property("ip_str", log.getSourceIp());
            properties.property("ip_long", IpUtils.ipToLong(log.getSourceIp()));
            properties.property("hit_count", log.getCount());
            properties.property("first_seen", log.getTimestamp());
            // 添加预计算网络地址
            Map<Integer, String> networks = IpUtils.precomputeNetworks(log.getSourceIp());
            networks.forEach((mask, network) ->
                    properties.property("net_" + mask, network)
            );
            vertices.add(properties);
        }
        // 批量提交（每批1000个）
        int batchSize = 1000;
        for (int i = 0; i < vertices.size(); i += batchSize) {
            List<Vertex> batch = vertices.subList(i, Math.min(i + batchSize, vertices.size()));
            graph.addVertices(batch);
        }
    }
}