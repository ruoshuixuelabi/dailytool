package com.xuexi.dailytool;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class PdfWatermarkWithImage {
    public void addImageWatermark(String pdfFilePath, String imageFilePath, String outputPdfFilePath, float opacity) throws IOException {
        // 加载PDF文档
        PDDocument document = Loader.loadPDF(new File(pdfFilePath));
        // 加载水印图片
        PDImageXObject pdImage = PDImageXObject.createFromFile(imageFilePath, document);
        pdImage.setWidth(50);
        pdImage.setHeight(50);
        // 遍历PDF文档的每一页
        for (PDPage page : document.getPages()) {
            // 创建内容流，用于在页面上绘制内容
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            // 设置透明度
//            contentStream.setNonStrokingAlphaConstant(opacity);
            // 获取页面尺寸
            PDRectangle pageSize = page.getMediaBox();
            // 计算图片在页面中的位置和大小
            float imageWidth = pdImage.getWidth();
            float imageHeight = pdImage.getHeight();
            float x = (pageSize.getWidth() - imageWidth) / 2;
            float y = (pageSize.getHeight() - imageHeight) / 2;

            // 在页面上绘制图片
            contentStream.drawImage(pdImage, x, y);

            // 关闭内容流
            contentStream.close();
        }

        // 保存加水印后的PDF文档
        document.save(outputPdfFilePath);

        // 关闭文档
        document.close();
    }

    public static void main(String[] args) {
        try {
            PdfWatermarkWithImage pdfWatermarkWithImage = new PdfWatermarkWithImage();
            pdfWatermarkWithImage.addImageWatermark("d://睿航至臻-网路边界安全巡逻系统-2025版.pdf", "d://QQ图片20250114155025.png", "d://xcd.pdf", 0.5f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}