package com.xuexi.dailytool.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author admin
 */
public class Man {
    public static void main(String[] args) throws IOException {
        Stream<Path> list = Files.list(Paths.get("H:\\深入剖析阿里核心微服务技术Spring Cloud Alibaba\\视频"));
        list.forEach(path -> {
            rename(path, "【瑞客论坛 www.ruike1.com】");
//            FileUtil.rename(path.toFile(), StrUtil.removeAll(path.toFile().toString(), "【瑞客论坛 www.ruike1.com】"), false, true);
        });
    }

    public static void rename(Path path, String name) {
        if (FileUtil.isDirectory(path.toFile())) {
            File rename = FileUtil.rename(path.toFile(), StrUtil.removeAll(path.toFile().toString(), name), false, true);
            File[] files = rename.listFiles();
            if (files != null) {
                for (File file : files) {
                    rename(file.toPath(), name);
                }
            }
        } else {
            try {
                FileUtil.rename(path.toFile(), StrUtil.removeAll(path.toFile().toString(), name), false, true);
            } catch (Exception e) {

            }
        }
    }

    private Godness god;

    public Man() {
    }

    public Man(Godness god) {
        this.god = god;
    }

    public Godness getGod() {
        return god;
    }

    public void setGod(Godness god) {
        this.god = god;
    }

    @Override
    public String toString() {
        return "Man [god=" + god + "]";
    }

}
