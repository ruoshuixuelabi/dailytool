package com.xuexi.dailytool.utils;

import org.apache.commons.net.util.SubnetUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class IpUtils {
    
    // 将IP字符串转为长整型
    public static long ipToLong(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        return (Long.parseLong(octets[0]) << 24) 
             | (Long.parseLong(octets[1]) << 16)
             | (Long.parseLong(octets[2]) << 8) 
             | Long.parseLong(octets[3]);
    }

    // 计算指定掩码的网络地址
    public static String calculateNetwork(String ip, int mask) {
        SubnetUtils utils = new SubnetUtils(ip + "/" + mask);
        return utils.getInfo().getNetworkAddress();
    }

    // 预生成所有掩码的网络地址
    public static Map<Integer, String> precomputeNetworks(String ip) {
        Map<Integer, String> networks = new TreeMap<>(Comparator.reverseOrder());
        for (int mask = 31; mask >= 13; mask--) {
            networks.put(mask, calculateNetwork(ip, mask));
        }
        return networks;
    }
}