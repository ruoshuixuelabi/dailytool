//import com.spire.doc.*;
//import com.spire.doc.documents.WatermarkLayout;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//
//import java.awt.*;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//public class WordTextWatermark {
//    public static void main(String[] args) {
//        //创建一个Document实例
//        Document document = new Document();
//        //加载示例 Word 文档
////        document.loadFromFile("D:\\关于印发《首钢京唐钢铁联合有限责任公司2025年推行本质化安全管理实施方案》的通知（第2版）_已加密_已解密.docx");
//        document.loadFromFile("D:\\5G安全接入网关-使用手册V1.3.docx");
//        //获取第一节
//        Section section = document.getSections().get(0);
//        //创建一个 TextWatermark 实例
//        TextWatermark txtWatermark = new TextWatermark();
//        //设置文本水印格式
//        txtWatermark.setText("内部使用");
//        txtWatermark.setFontSize(40);
//        txtWatermark.setColor(Color.red);
//        txtWatermark.setLayout(WatermarkLayout.Diagonal);
//        //将文本水印添加到示例文档
//        section.getDocument().setWatermark(txtWatermark);
//        //保存文件
//        document.saveToFile("d://5G安全接入网关-使用手册V1.3.docxshuiyin.docx", FileFormat.Docx);
//        restWord("d://5G安全接入网关-使用手册V1.3.docxshuiyin.docx");
//    }
//
//    private static void restWord(String docFilePath) {
//        try (FileInputStream in = new FileInputStream(docFilePath)) {
//            XWPFDocument doc = new XWPFDocument(OPCPackage.open(in));
//            List<XWPFParagraph> paragraphs = doc.getParagraphs();
//            if (paragraphs.size() < 1) return;
//            XWPFParagraph firstParagraph = paragraphs.get(0);
//            if (firstParagraph.getText().contains("Spire.Doc")) {
//                doc.removeBodyElement(doc.getPosOfParagraph(firstParagraph));
//            }
//            OutputStream out = new FileOutputStream(docFilePath);
//            doc.write(out);
//            out.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}