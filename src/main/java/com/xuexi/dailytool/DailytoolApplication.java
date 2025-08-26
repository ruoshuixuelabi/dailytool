package com.xuexi.dailytool;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.poi.hwpf.HWPFDocument;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import java.io.*;

@SpringBootApplication
@MapperScan("com.xuexi.dailytool.mapper")
public class DailytoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailytoolApplication.class, args);
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("mulpartion")
                .partitions(4)
                .replicas(1)
                .compact()
                .build();
    }
}
