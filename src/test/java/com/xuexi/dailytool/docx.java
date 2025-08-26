package com.xuexi.dailytool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Demonstrates how to add pictures in a .docx document
 */
@SuppressWarnings({"java:S106", "java:S4823", "java:S1192"})
public final class docx {

    private docx() {
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String existingDocxPath = "d://关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx";
        String imgFile = "d://QQ图片20250222154447.png";
//        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(existingDocxPath))) {
//            // Read the picture data from the file
//            byte[] pictureData;
//            try (FileInputStream is = new FileInputStream(imgFile)) {
//                pictureData = is.readAllBytes();
//            }
//
//            // Add the picture data to the document
//            doc.addPictureData(pictureData, PictureType.PNG);
//
//            // Traverse all paragraphs and add the picture at the beginning of each page
//            for (XWPFParagraph paragraph : doc.getParagraphs()) {
//                XWPFRun run = paragraph.createRun();
//                run.addPicture(new FileInputStream(imgFile), PictureType.PNG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
//                run.addBreak(BreakType.PAGE);
//            }
//
//            // Save the modified document as a new file
//            try (FileOutputStream out = new FileOutputStream("d://1213213213213213静态脱敏.docx")) {
//                doc.write(out);
//            }
//        }
    }

}
