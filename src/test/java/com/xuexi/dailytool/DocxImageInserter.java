package com.xuexi.dailytool;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocxImageInserter {
    public static void main(String[] args) {
        String docxFilePath = "d://关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx"; // DOCX 文件路径
        String imageFilePath = "d:/QQ图片20250114155025.png"; // PNG 图片路径
        String outputFilePath = "d:/1232132132132132132132.docx"; // 输出文件路径

        try (XWPFDocument document = new XWPFDocument(new FileInputStream(docxFilePath))) {
            // 获取文档的页眉页脚策略
            XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
            if (headerFooterPolicy != null) {
                // 获取默认页脚、首页页脚和偶数页页脚，并为它们添加图片
                addImageToFooter(headerFooterPolicy.getDefaultFooter(), imageFilePath);
                addImageToFooter(headerFooterPolicy.getFirstPageFooter(), imageFilePath);
                addImageToFooter(headerFooterPolicy.getEvenPageFooter(), imageFilePath);
            }

            // 保存修改后的文档
            try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
                document.write(out);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private static void addImageToFooter(XWPFFooter footer, String imageFilePath) throws IOException, InvalidFormatException {
        if (footer != null) {
            // 检查页脚是否已经包含图片
            boolean imageExists = false;
            for (XWPFParagraph paragraph : footer.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    if (run.getEmbeddedPictures().size() > 0) {
                        imageExists = true;
                        break;
                    }
                }
                if (imageExists) {
                    break;
                }
            }

            if (!imageExists) {
                // 创建一个新的段落
                XWPFParagraph paragraph = footer.createParagraph();
                XWPFRun run = paragraph.createRun();

                // 使用 try-with-resources 确保图片流正确关闭
                try (FileInputStream imageStream = new FileInputStream(imageFilePath)) {
                    // 插入图片
                    run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, imageFilePath, Units.toEMU(100), Units.toEMU(100)); // 设置图片大小
                }

                run.addBreak(); // 添加换行
            }
        }
    }
}