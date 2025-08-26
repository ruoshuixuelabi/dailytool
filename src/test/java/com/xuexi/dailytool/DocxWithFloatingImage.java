package com.xuexi.dailytool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public final class DocxWithFloatingImage {

    private DocxWithFloatingImage() {
    }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//        String existingDocxPath = "d://静态脱敏.docx";
//        String imgFile = "d://QQ图片20250114155025.png";
//        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(existingDocxPath))) {
//            // Read the picture data from the file
//            byte[] pictureData;
//            try (FileInputStream is = new FileInputStream(imgFile)) {
//                pictureData = is.readAllBytes();
//            }
//
//            // Add the picture data to the document
//            String pictureIndex = doc.addPictureData(pictureData, PictureType.PNG);
//
//            // 遍历所有段落并在每个段落后添加图片
//            for (XWPFParagraph paragraph : doc.getParagraphs()) {
//                // 在段落后添加一个新的段落
////                XWPFParagraph imageParagraph = doc.createParagraph();
//                XWPFRun run = paragraph.createRun();
//
//                // 添加浮动图片
//                run.addPicture(new FileInputStream(imgFile), PictureType.PNG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
//
//                // 添加分页符，确保每个图片在新的一页
//                run.addBreak();
//                run.addBreak();
//            }
//
//            // Save the modified document as a new file
//            try (FileOutputStream out = new FileOutputStream("d://1213213213213213静态脱敏111.docx")) {
//                doc.write(out);
//            }
//        }
//    }
}
