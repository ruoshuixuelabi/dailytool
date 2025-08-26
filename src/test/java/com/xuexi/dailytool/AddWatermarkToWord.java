//package com.xuexi.dailytool;
//
//import jakarta.xml.bind.JAXBElement;
//import jakarta.xml.bind.JAXBException;
//import org.docx4j.XmlUtils;
//import org.docx4j.jaxb.Context;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
//import org.docx4j.wml.*;
//
//import javax.xml.namespace.QName;
//
//public class AddWatermarkToWord {
//    public static void main(String[] args) throws Exception {
//        // 创建一个新的 Word 文档
//        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
//
//        // 获取主文档部分
//        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
//
//        // 添加一个段落
//        mainDocumentPart.addParagraphOfText("This is a sample document with a watermark.");
//
//        // 添加页眉部分
//        HeaderPart headerPart = new HeaderPart();
//        mainDocumentPart.getHeaderFooterPolicy().addHeader(headerPart);
//
//        // 创建水印文本
//        String watermarkText = "Confidential";
//        P watermarkParagraph = createWatermarkParagraph(watermarkText);
//
//        // 将水印段落添加到页眉
//        headerPart.getContent().add(watermarkParagraph);
//
//        // 保存文档
//        wordPackage.save(new java.io.File("WatermarkedDocument.docx"));
//    }
//
//    /**
//     * 创建水印段落
//     * @param text 水印文本
//     * @return 水印段落
//     */
//    private static P createWatermarkParagraph(String text) throws JAXBException {
//        P paragraph = new P();
//        R run = new R();
//        JAXBElement<Text> textElement = new JAXBElement<>(new QName("w", "t"), Text.class, text);
//        Text textContent = Context.getWmlObjectFactory().createText();
//        textContent.setValue(text);
//        textElement.setValue(textContent);
//        run.getContent().add(textElement);
//
//        // 设置样式（如字体大小、颜色等）
//        RPr rPr = Context.getWmlObjectFactory().createRPr();
////        rPr.setSz(50); // 字体大小
////        rPr.setColor("D3D3D3"); // 灰色
//        run.setRPr(rPr);
//
//        paragraph.getContent().add(run);
//        return paragraph;
//    }
//}