//package com.xuexi.dailytool;
//
//import cn.hutool.core.io.FileUtil;
//import com.aspose.words.Document;
//import com.aspose.words.TextWatermarkOptions;
//import java.awt.*;
//import com.rhzz.securitytools.util.SecurityTools;
//
//public class aoi {
//    public static void main(String[] args) throws Exception {
//        byte[] bytes = SecurityTools.addWatermark("d:\\QQ图片20250222154447.png",
//                true, true, "terminalUser", "waterMarkerText");
//        FileUtil.writeBytes(bytes,"d:\\123.png" );
//        System.out.println(bytes);
//
//
////        Document doc = new Document("D:\\关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx");
////        Document doc = new Document("D:\\测试doc文档.doc");
//////        Document doc = new Document("D:\\5G安全接入网关-使用手册V1.3.docx");
//////        Document doc = new Document("D:\\20250103-数据安全管控平台项目五期-技术需求说明书_v6.docx");
//////        Document doc = new Document("D:\\5G安全接入网关-使用手册V1.3 - 副本.docx");
////// Set watermark options
////        TextWatermarkOptions watermarkOptions = new TextWatermarkOptions();
////        watermarkOptions.setFontSize(36);
////        watermarkOptions.setFontFamily("Arial");
////        watermarkOptions.setColor(Color.RED);
////        watermarkOptions.setLayout(45);
////        watermarkOptions.isSemitrasparent(true);
////// Insert watermark
////        doc.getWatermark().setText("首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案", watermarkOptions);
////// Save the updated document
////        doc.save("d:\\测试doc文档.doc-watermark.docx");
//    }
//}
