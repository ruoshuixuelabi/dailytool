package com.xuexi.dailytool.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.hugegraph.driver.GraphManager;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.driver.SchemaManager;
import org.apache.hugegraph.structure.graph.Edge;
import org.apache.hugegraph.structure.graph.Vertex;
import org.apache.hugegraph.structure.schema.PropertyKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GraphClient {

    private static final String GRAPH_NAME = "hugegraph4";
    private static final String SERVER_URL = "http://192.168.1.237:8088";

    public static HugeClient getClient() {
        HugeClient client = HugeClient.builder(SERVER_URL, GRAPH_NAME)
                .build();
        // 自动创建图空间（关键修复）
//        try {
//            client.serverInfo().getGraphs();
//        } catch (NotFoundException e) {
//            client.createGraph(new GraphCreateRequest()
//                    .name(GRAPH_NAME)
//                    .config(new HashMap<>()));
//        }
        return client;
    }

    public static void initSchema() {
        HugeClient client = getClient();
        SchemaManager schema = client.schema();

        // 创建顶点类型

//        schema.vertexLabel("person").properties("name", "age").ifNotExist().create();

        // 必须先定义属性键（关键修复）
        schema.propertyKey("ip_str").asText().ifNotExist().create();
        schema.propertyKey("ip_long").asLong().ifNotExist().create();
        schema.propertyKey("hit_count").asInt().ifNotExist().create();
        schema.propertyKey("first_seen").asDate().ifNotExist().create(); // 新增
        // 动态创建网络属性键（net_31到net_13）
        for (int mask = 31; mask >= 13; mask--) {
            String propName = "net_" + mask;
            schema.propertyKey(propName).asText().ifNotExist().create();
        }
        List<String> ipProperties = new ArrayList<>();
        ipProperties.add("ip_str");
        ipProperties.add("ip_long");
        ipProperties.add("hit_count");
        ipProperties.add("first_seen");

        // 添加所有网络掩码属性（关键修复）
        for (int mask = 31; mask >= 13; mask--) {
            ipProperties.add("net_" + mask);
        }


        // 继续其他属性键定义
        schema.propertyKey("network").asText().ifNotExist().create();
        schema.propertyKey("mask").asInt().ifNotExist().create();
        schema.propertyKey("child_count").asInt().ifNotExist().create();

        // 然后创建顶点标签
        schema.vertexLabel("IP")
//                .properties("ip_str", "ip_long", "hit_count", "first_seen")
                .properties(ipProperties.toArray(new String[0]))
                .primaryKeys("ip_str")
                .ifNotExist()
                .create();

//        schema.vertexLabel("IP")
//                .properties("ip_str", "ip_long", "hit_count", "first_seen")
//                .primaryKeys("ip_str")
//                .ifNotExist()
//                .create();

        schema.vertexLabel("Subnet")
                .properties("network", "mask", "child_count")
                .primaryKeys("network", "mask")
                .ifNotExist()
                .create();

        // 创建边类型
        schema.edgeLabel("belong_to")
                .sourceLabel("IP").targetLabel("Subnet")
                .ifNotExist()
                .create();

        schema.edgeLabel("merged_from")
                .sourceLabel("Subnet").targetLabel("IP")
                .ifNotExist()
                .create();
    }

    public static void initSchema2() {
        HugeClient client = getClient();
        SchemaManager schema = client.schema();
        // 定义所有属性键（基于实体类完整字段）
        Map<String, PropertyKey> keys = new LinkedHashMap<>();
        keys.put("uuid", schema.propertyKey("uuid").asText().ifNotExist().create());
        keys.put("deviceId", schema.propertyKey("deviceId").asLong().ifNotExist().create());
        keys.put("time", schema.propertyKey("time").asDate().ifNotExist().create());
        keys.put("policyId", schema.propertyKey("policyId").asInt().ifNotExist().create());
        keys.put("policyName", schema.propertyKey("policyName").asText().ifNotExist().create());
        keys.put("protocol", schema.propertyKey("protocol").asText().ifNotExist().create());
        keys.put("srcPort", schema.propertyKey("srcPort").asInt().ifNotExist().create());
        keys.put("dstPort", schema.propertyKey("dstPort").asInt().ifNotExist().create());
        keys.put("srcZone", schema.propertyKey("srcZone").asText().ifNotExist().create());
        keys.put("dstZone", schema.propertyKey("dstZone").asText().ifNotExist().create());
        keys.put("action", schema.propertyKey("action").asInt().ifNotExist().create());
        keys.put("keyType", schema.propertyKey("keyType").asText().ifNotExist().create());
        keys.put("vsys", schema.propertyKey("vsys").asText().ifNotExist().create());
        keys.put("vsysId", schema.propertyKey("vsysId").asInt().ifNotExist().create());
        keys.put("frequency", schema.propertyKey("frequency").asLong().ifNotExist().create());
        keys.put("ip_str", schema.propertyKey("ip_str").asText().ifNotExist().create());

        // IP顶点类型（包含所有相关属性）
        schema.vertexLabel("IP")
                .properties(keys.keySet().toArray(new String[0]))
                .primaryKeys("ip_str")
                .ifNotExist()
                .create();

        // 通信关系边类型（携带完整流量特征）
        schema.edgeLabel("TrafficFlow")
                .sourceLabel("IP")
                .targetLabel("IP")
                .properties("protocol", "action", "srcPort", "dstPort",
                        "time", "policyId", "srcZone", "dstZone",
                        "vsysId", "frequency")
                .ifNotExist()
                .create();
    }

    public static void insertFlow(PolicyLogBean flow) {
        HugeClient client = getClient();

        // 构造源IP顶点（包含所有属性）
        Vertex src = new Vertex("IP")
//                .id(VertexId.of("IP", "ip_str", flow.getSrc())) // 显式构造ID
                .property("uuid", flow.getUuid())
                .property("deviceId", flow.getDeviceId())
                .property("policyId", flow.getPolicyId())
                .property("policyName", flow.getPolicyName())
                .property("protocol", flow.getProtocol())
                .property("srcPort", flow.getSrcPort())
                .property("dstPort", flow.getDstPort())
                .property("srcZone", flow.getSrcZone())
                .property("dstZone", flow.getDstZone())
                .property("action", flow.getAction())
                .property("keyType", flow.getKeyType())
                .property("vsys", flow.getVsys())
                .property("frequency", flow.getFrequency())
                .property("ip_str", flow.getSrc())
                .property("vsysId", flow.getVsysId())
                .property("time", flow.getTime());

        // 构造目的IP顶点
        Vertex dst = new Vertex("IP")
                .property("uuid", flow.getUuid())
                .property("policyId", flow.getPolicyId())
                .property("policyName", flow.getPolicyName())
                .property("protocol", flow.getProtocol())
                .property("srcPort", flow.getSrcPort())
                .property("dstPort", flow.getDstPort())
                .property("srcZone", flow.getSrcZone())
                .property("dstZone", flow.getDstZone())
                .property("action", flow.getAction())
                .property("keyType", flow.getKeyType())
                .property("vsys", flow.getVsys())
                .property("frequency", flow.getFrequency())
                .property("ip_str", flow.getDst())
                .property("deviceId", flow.getDeviceId()) // 假设目的设备相同
                .property("vsysId", flow.getVsysId())
                .property("time", flow.getTime());

        // 批量提交顶点
        client.graph().addVertices(Arrays.asList(src, dst));

        // 创建携带完整属性的边
        Edge traffic = new Edge("TrafficFlow")
                .source(src)
                .target(dst)
                .property("protocol", flow.getProtocol())
                .property("action", flow.getAction())
                .property("srcPort", flow.getSrcPort())
                .property("dstPort", flow.getDstPort())
                .property("time", flow.getTime())
                .property("policyId", flow.getPolicyId())
                .property("srcZone", flow.getSrcZone())
                .property("dstZone", flow.getDstZone())
                .property("vsysId", flow.getVsysId())
                .property("frequency", flow.getFrequency());
        client.graph().addEdge(traffic);
    }

    public List<PolicyLogBean> generateFullTestData() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Arrays.asList(
                // 正常Web流量
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1001L)
                        .setTime(new Date())
                        .setPolicyId(2001)
                        .setPolicyName("Web访问策略")
                        .setSrc("192.168.1.100")
                        .setDst("10.0.0.50")
                        .setProtocol("TCP")
                        .setSrcPort(54321)
                        .setDstPort(80)
                        .setSrcZone("trust")
                        .setDstZone("untrust")
                        .setAction(1)
                        .setKeyType("业务流量")
                        .setVsys("vsys1")
                        .setVsysId(1)
                        .setFrequency(150L),

                // DNS查询被拒绝
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1002L)
                        .setTime(new Date(sdf.parse("2024-03-20 14:30:00").getTime()))
                        .setPolicyId(2002)
                        .setPolicyName("DNS限制策略")
                        .setSrc("172.16.8.200")
                        .setDst("8.8.8.8")
                        .setProtocol("UDP")
                        .setSrcPort(12345)
                        .setDstPort(53)
                        .setSrcZone("dmz")
                        .setDstZone("external")
                        .setAction(0)
                        .setKeyType("可疑流量")
                        .setVsys("vsys2")
                        .setVsysId(2)
                        .setFrequency(5L),

                // 内部PING探测
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1003L)
                        .setTime(new Date(sdf.parse("2024-03-20 15:00:00").getTime()))
                        .setPolicyId(3001)
                        .setPolicyName("内部监控策略")
                        .setSrc("10.0.1.5")
                        .setDst("10.0.1.6")
                        .setSrcPort(3100)
                        .setDstPort(3200)
                        .setProtocol("ICMP")
                        .setSrcZone("internal")
                        .setDstZone("internal")
                        .setAction(1)
                        .setKeyType("健康检查")
                        .setVsys("vsys3")
                        .setVsysId(3)
                        .setFrequency(60L)
        );
    }

    public static void main(String[] args) throws ParseException {
        initSchema2();
        // 初始化批量提交器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<PolicyLogBean> list2=new ArrayList<>();
        List<PolicyLogBean> list = Arrays.asList(
                // 正常Web流量
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1001L)
                        .setTime(new Date())
                        .setPolicyId(2001)
                        .setPolicyName("Web访问策略")
                        .setSrc("192.168.1.100")
                        .setDst("10.0.0.50")
                        .setProtocol("TCP")
                        .setSrcPort(54321)
                        .setDstPort(80)
                        .setSrcZone("trust")
                        .setDstZone("untrust")
                        .setAction(1)
                        .setKeyType("业务流量")
                        .setVsys("vsys1")
                        .setVsysId(1)
                        .setFrequency(150L),

                // DNS查询被拒绝
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1002L)
                        .setTime(new Date(sdf.parse("2024-03-20 14:30:00").getTime()))
                        .setPolicyId(2002)
                        .setPolicyName("DNS限制策略")
                        .setSrc("172.16.8.200")
                        .setDst("8.8.8.8")
                        .setProtocol("UDP")
                        .setSrcPort(12345)
                        .setDstPort(53)
                        .setSrcZone("dmz")
                        .setDstZone("external")
                        .setAction(0)
                        .setKeyType("可疑流量")
                        .setVsys("vsys2")
                        .setVsysId(2)
                        .setFrequency(5L),

                // 内部PING探测
                new PolicyLogBean()
                        .setUuid(UUID.randomUUID().toString())
                        .setDeviceId(1003L)
                        .setTime(new Date(sdf.parse("2024-03-20 15:00:00").getTime()))
                        .setPolicyId(3001)
                        .setPolicyName("内部监控策略")
                        .setSrc("10.0.1.5")
                        .setDst("10.0.1.6")
                        .setSrcPort(12345)
                        .setDstPort(53)
                        .setProtocol("ICMP")
                        .setSrcZone("internal")
                        .setDstZone("internal")
                        .setAction(1)
                        .setKeyType("健康检查")
                        .setVsys("vsys3")
                        .setVsysId(3)
                        .setFrequency(60L)
        );
        for (int i = 0; i < 1000; i++) {
            PolicyLogBean policyLogBean = new PolicyLogBean()
                    .setUuid(UUID.randomUUID().toString())
                    .setDeviceId(1003L)
                    .setTime(new Date(sdf.parse("2024-03-20 15:00:00").getTime()))
                    .setPolicyId(3001)
                    .setPolicyName("内部监控策略")
                    .setSrc(generateRandomIP())
                    .setDst(generateRandomIP())
                    .setSrcPort(RandomUtil.randomInt(65535))
                    .setDstPort(RandomUtil.randomInt(65535))
                    .setProtocol("ICMP")
                    .setSrcZone("internal")
                    .setDstZone("internal")
                    .setAction(1)
                    .setKeyType("健康检查")
                    .setVsys("vsys3")
                    .setVsysId(3)
                    .setFrequency(60L);
            list2.add(policyLogBean);
        }
        for (PolicyLogBean flow : list2) {
            insertFlow(flow);
        }
    }

    // 生成随机的IP地址，范围为10.0.1.0到10.0.1.255
    private static String generateRandomIP() {
        int lastOctet = (int) (Math.random() * 256); // 生成0到255的随机数
        return "10.0.1." + lastOctet;
    }

}
