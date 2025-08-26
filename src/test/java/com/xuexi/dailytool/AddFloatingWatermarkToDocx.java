package com.xuexi.dailytool;

import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddFloatingWatermarkToDocx {
    public static void main(String[] args) throws Exception {
        // 加载现有的 docx 文档
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("d://静态脱敏.docx"));
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();

        // 读取图片水印
        BufferedImage watermarkImage = ImageIO.read(new File("d://QQ图片20250114155025.png"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(watermarkImage, "png", out);
        byte[] imageBytes = out.toByteArray();

        // 创建图片部分
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, imageBytes);
        Inline inline = imagePart.createImageInline("watermark.png", "Watermark", 0, 1, false);
        String imageRelId = inline.getGraphic().getGraphicData().getPic().getBlipFill().getBlip().getEmbed();

        // 添加图片水印到文档的页眉中
        HeaderPart headerPart = new HeaderPart();
        Relationship headerRelationship = mainDocumentPart.addTargetPart(headerPart);
        addFloatingWatermark(headerPart, imageRelId);

        // 将页眉关联到文档的默认节
        SectPr sectPr = wordMLPackage.getDocumentModel().getSections().get(wordMLPackage.getDocumentModel().getSections().size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = Context.getWmlObjectFactory().createSectPr();
            mainDocumentPart.addObject(sectPr);
        }
        HeaderReference headerReference = Context.getWmlObjectFactory().createHeaderReference();
        headerReference.setType(HdrFtrRef.DEFAULT);
        headerReference.setId(headerRelationship.getId());
        sectPr.getEGHdrFtrReferences().add(headerReference);

        // 保存修改后的文档
        wordMLPackage.save(new File("d://123.docx"));
    }

    private static void addFloatingWatermark(HeaderPart headerPart, String imageRelId) throws Exception {
        String openXML = "<w:hdr xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">"
                + "<w:p>"
                + "<w:pPr>"
                + "<w:pStyle w:val=\"Header\"/>"
                + "</w:pPr>"
                + "<w:r>"
                + "<w:pict>"
                + "<v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\" joinstyle=\"miter\">"
                + "<v:stroke joinstyle=\"miter\"/>"
                + "<v:path gradientshapeok=\"t\" o:connecttype=\"rect\"/>"
                + "</v:shapetype>"
                + "<v:shape id=\"Watermark\" o:spid=\"_x0000_s1026\" type=\"#_x0000_t75\" style=\"position:absolute;margin-left:0;margin-top:0;width:467.95pt;height:615.75pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\" wrap=\"none\" filled=\"f\" fillcolor=\"white [7]\" stroked=\"f\" o:cliptowrap=\"t\">"
                + "<v:fill color2=\"white [7]\"/>"
                + "<v:stroke dashstyle=\"solid\" miterlimit=\"8\" joinstyle=\"miter\"/>"
                + "<v:imagedata r:id=\"" + imageRelId + "\" o:title=\"Watermark\"/>"
                + "</v:shape>"
                + "</w:pict>"
                + "</w:r>"
                + "</w:p>"
                + "</w:hdr>";

        Hdr hdr = (Hdr) XmlUtils.unmarshalString(openXML);
        headerPart.setJaxbElement(hdr);
    }
}