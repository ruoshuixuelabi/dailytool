package com.xuexi.dailytool.utils;

import org.apache.hugegraph.driver.GraphManager;
import org.apache.hugegraph.driver.GremlinManager;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.graph.Edge;
import org.apache.hugegraph.structure.graph.Vertex;
import org.apache.hugegraph.structure.gremlin.Result;
import org.apache.hugegraph.structure.gremlin.ResultSet;

import java.util.*;
import java.util.function.Consumer;

public class SubnetMerger {

    private static final int MAX_SUBNETS = 100;

    public void mergeSubnets() {
        HugeClient client = GraphClient.getClient();
        GremlinManager gremlin = client.gremlin();

        int currentMask = 31;
        int subnetCount = Integer.MAX_VALUE;

        while (currentMask >= 13 && subnetCount > MAX_SUBNETS) {
//            String query = String.format(
//                    "g.V().hasLabel('IP').has('net_%d')" +
//                            ".group().by('net_%d').by(count())" +
//                            ".unfold().filter(select(Column.values).is(gt(1)))", // 使用Column.values
//                    currentMask, currentMask);
            String query = String.format(
                    "g.V().hasLabel('IP').has('net_%d')" +
                            ".group().by('net_%d')" +
                            ".unfold().as('entry')" + // 明确声明为entry
                            ".select('entry')", // 选择整个entry
                    currentMask, currentMask
            );
            ResultSet result = gremlin.gremlin(query).execute();
            processMergeResult(result, currentMask);
            // 检查当前子网数量
            subnetCount = countSubnets();
            currentMask--;
        }
    }

    private void processMergeResult(ResultSet result, int mask) {
        // 修正遍历方式
        Iterator<Result> iterator = result.iterator();
        while (iterator.hasNext()) {
            try {
                // 获取原始条目（Entry类型）
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next().getObject();

                // 提取分组键（网络地址）
                String network = (String) entry.getKey();

                // 提取分组值（IP列表或计数）
                Object value = entry.getValue();

                // 处理不同类型的值
                if (value instanceof List) {
                    List<Vertex> ips = (List<Vertex>) value;
                    createSubnet(network, mask, ips);
                } else if (value instanceof Long) {
                    long count = (Long) value;
                    if (count > 1) {
                        // 需要额外查询获取实际IP列表
                        fetchAndCreateSubnet(network, mask);
                    }
                }
            } catch (ClassCastException e) {
//                log.error("Invalid result format: {}", e.getMessage());
            }
        }
    }
    private void fetchAndCreateSubnet(String network, int mask) {
        String query = String.format(
                "g.V().hasLabel('IP').has('net_%d', '%s')",
                mask, network
        );
        List<Vertex> ips=new ArrayList<>();
        GremlinManager gremlin = GraphClient.getClient().gremlin();
        ResultSet result = gremlin.gremlin(query).execute();
        while (result.iterator().hasNext()) {
            Vertex ip = result.iterator().next().getVertex();
            ips.add(ip);

        }
        createSubnet(network, mask, ips);
    }
    private void createSubnet(String network, int mask, List<Vertex> ips) {
        HugeClient client = GraphClient.getClient();
        GraphManager graph = client.graph();
        // 1. 创建子网顶点
        Vertex subnet = new Vertex("Subnet");
        subnet.property("network", network);
        subnet.property("mask", mask);
        subnet.property("child_count", ips.size());
        graph.addVertex(subnet);
        // 2. 批量创建归属关系
        List<Edge> edges = new ArrayList<>();
        for (Vertex ip : ips) {
            edges.add(new Edge("belong_to")
                    .source(ip)
                    .target(subnet)
                    .property("create_time", new Date()));
        }
        graph.addEdges(edges);

        // 3. 记录合并来源
        Edge mergeEdge = new Edge("merged_from")
                .source(subnet)
                .target(ips.get(0)) // 记录任一来源示例
                .property("merge_time", new Date());
        graph.addEdge(mergeEdge);
    }

    private int countSubnets() {
        GremlinManager gremlin = GraphClient.getClient().gremlin();
        String query = "g.V().hasLabel('Subnet').count()";
        ResultSet result = gremlin.gremlin(query).execute();
        return result.get(0).getInt();
    }
}