package com.xuexi.dailytool.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class FileRename {
    public static void main(String[] args) throws IOException {
        try (Stream<Path> list = Files.list(Paths.get("H:\\深入剖析阿里核心微服务技术Spring Cloud Alibaba\\视频"))) {
            list.forEach(path -> rename(path, "【瑞客论坛 www.ruike1.com】"));
        }
    }

    public static void rename(Path path, String name) {
        if (FileUtil.isDirectory(path.toFile())) {
            File[] files = path.toFile().listFiles();
            if (files != null) {
                Arrays.stream(files).map(File::toPath).forEach(p -> rename(p, name));
            }
        } else {
            String fileName = path.getFileName().toString();
            if (fileName.contains(name)) {
                try {
                    FileUtil.rename(path.toFile(), StrUtil.removeAll(path.toFile().toString(), name), false, true);
                } catch (Exception e) {
                    log.error("重命名失败: {}", e.getMessage(), e);
                }
            }
        }
    }
}
