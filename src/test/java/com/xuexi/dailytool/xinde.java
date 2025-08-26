package com.xuexi.dailytool;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class xinde {
    @Test
    public void testExecuteCommandword() throws IOException, InvalidFormatException {
        byte[] bytes1 = FileUtil.readBytes("D:\\关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx");
        BufferedInputStream inputStream = FileUtil.getInputStream("D:\\关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx");
        // 创建一个新的Word文档
        XWPFDocument document = new XWPFDocument(inputStream);
        // 创建页眉
        XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
        if (headerFooterPolicy == null) {
            headerFooterPolicy = document.createHeaderFooterPolicy();
        }
        XWPFHeader header = headerFooterPolicy.createHeader(STHdrFtr.DEFAULT);
        XWPFHeader wvenheader = headerFooterPolicy.createHeader(STHdrFtr.EVEN);
        XWPFHeader wvenheaderf = headerFooterPolicy.createHeader(STHdrFtr.FIRST);
        // 清空页眉中的所有段落
        List<XWPFParagraph> paragraphs = new ArrayList<>(header.getParagraphs());
        List<XWPFParagraph> paragraphswvenheader = new ArrayList<>(wvenheader.getParagraphs());
        List<XWPFParagraph> paragraphswvenheaderf = new ArrayList<>(wvenheaderf.getParagraphs());
        extracted(paragraphs, header);
        extracted(paragraphswvenheader, wvenheader);
        extracted(paragraphswvenheaderf, wvenheaderf);
        // 保存文档
        try (FileOutputStream out = new FileOutputStream("D:\\output.docx")) {
            document.write(out);
        }
    }
    private static void extracted(List<XWPFParagraph> paragraphs, XWPFHeader header) throws IOException {
        for (XWPFParagraph paragraph : paragraphs) {
            header.removeParagraph(paragraph);
        }
        // 创建一个新的段落并添加水印
        XWPFParagraph paragraph = header.createParagraph();
        CTP ctp = paragraph.getCTP();
        CTPPr ppr = ctp.addNewPPr();
        ppr.addNewPStyle().setVal("Header");
        // 添加图片水印
        int format = XWPFDocument.PICTURE_TYPE_PNG;
        try (FileInputStream imageStream = new FileInputStream("D:\\QQ图片20250222154447.png")) {
            XWPFRun run = paragraph.createRun();
            run.addPicture(imageStream, format, "QQ图片20250222154447.png", Units.toEMU(200), Units.toEMU(200));
        }
        catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
