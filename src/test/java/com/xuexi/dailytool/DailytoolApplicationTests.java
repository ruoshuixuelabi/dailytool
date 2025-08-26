package com.xuexi.dailytool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.xuexi.dailytool.mapper.AssetDataRuleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class DailytoolApplicationTests {
    @Autowired
    AssetDataRuleMapper assetDataRuleMapper;
    @Autowired
    KafkaTemplate kafkaTemplate;

    @Test
    void kafkaSend() {
        while (true) {
//            kafkaTemplate.send("testsend", "test");
//            kafkaTemplate.send("mulpartion", RandomUtil.randomString(10),"test");
        }
    }

    @Test
    void contextLoads() {
        List<AssetDataRule> assetDataRules = assetDataRuleMapper.selectList(null);
        String fileName = "D:\\test4.xlsx";
        List<DemoData> data = new ArrayList<>();
        Set<String> sqls = new HashSet<>();
        for (int i = 0; i < assetDataRules.size(); i++) {
            AssetDataRule assetDataRule1 = assetDataRules.get(i);
            String attributeValue1 = assetDataRule1.getAttributeValue();
            for (int i1 = 0; i1 < assetDataRules.size(); i1++) {
                AssetDataRule assetDataRule = assetDataRules.get(i1);
                String attributeValue = assetDataRule.getAttributeValue();
                if (!attributeValue1.equals(attributeValue) && attributeValue1.contains(attributeValue)) {
                    DemoData demoData = new DemoData();
                    demoData.setString(attributeValue1);
                    demoData.setString2(attributeValue);
                    demoData.setCondition(assetDataRule1.getCondition());
                    demoData.setIndex2(String.valueOf(assetDataRule.getId()));
                    demoData.setIndex1(String.valueOf(assetDataRule1.getId()));
                    demoData.setCondition2(assetDataRule.getCondition());
                    data.add(demoData);
                    // update asset_data_rule set set condition_='regex' ,attribute_value=concat('^',attribute_value,'$') where
                    // length(attribute_value)<5 and condition_='contain'
                    sqls.add("update asset_data_rule_3 set condition_='regex' ,attribute_value=concat('^',attribute_value,'$')  where attribute_value='" + attributeValue + "'");
                }
            }
        }
        FileUtil.writeLines(sqls, "d://sql2.sql", "UTF-8");
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(data);
    }
}
