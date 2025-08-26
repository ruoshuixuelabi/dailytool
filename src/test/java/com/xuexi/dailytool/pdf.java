package com.xuexi.dailytool;

import com.aspose.slides.License;
import com.aspose.slides.PdfOptions;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.InputStream;

public class pdf {
    public static void main(String[] args) {
//        modifyWordsJar();
//        modifyExcelJar();
//        modifyPDFJar();
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("license.xml");
//        License license = new License();
//        license.setLicense(is);
//        String sourceFile = "D:\\个人信息保护政策.pdf";//输入的文件
//        String targetFile = "D:\\800M.docx";//输出的文件
//        pdf2doc(sourceFile, targetFile);
//        modifyPptJar();
        //给pdf文件添加图片水印，图片读取本地目录获取
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("license.xml");
//        License license = new License();
//        license.setLicense(is);
//        Presentation presentation = new Presentation("D:\\360MoveData\\Users\\123456\\Documents\\Tencent Files\\524991368\\FileRecv\\师生间进行有效沟通的几点.pptx");
        try {
            // Instantiate the PdfOptions class.
//            PdfOptions pdfOptions = new PdfOptions();
            // Add hidden slides.
//            pdfOptions.setShowHiddenSlides(true);
            // Save the presentation as a PDF.
//            presentation.save("D:\\PowerPoint-to-PDF1.pdf", SaveFormat.Pdf, pdfOptions);
        }
        finally {
//            presentation.dispose();
        }
    }
    //未授权认证情况：会有水印和数量(页数)限制
    @Test
    public void pdf2doc_test() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("license.xml");
        License license = new License();
        license.setLicense(is);
        String sourceFile = "D:\\个人信息保护政策.pdf";//输入的文件
        String targetFile = "D:\\800M.docx";//输出的文件
        pdf2doc(sourceFile, targetFile);
    }

    /**
     * PDF转Word操作
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static void pdf2doc(String sourceFile, String targetFile) {
        try {
            long old = System.currentTimeMillis();
            FileOutputStream os = new FileOutputStream(targetFile);
            com.aspose.pdf.Document doc = new com.aspose.pdf.Document(sourceFile);//加载源文件数据
            doc.save(os, com.aspose.pdf.SaveFormat.DocX);//设置转换文件类型并转换
            os.close();
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改slides.jar包里面的校验
     */
    public static void modifyPptJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("D:\\aspose-slides-21.10-jdk16.jar");
            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.slides.internal.oh.public");
            CtMethod[] methodA = zzZJJClass.getDeclaredMethods();
            for (CtMethod ctMethod : methodA) {
                CtClass[] ps = ctMethod.getParameterTypes();
                if (ps.length == 3 && ctMethod.getName().equals("do")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    ctMethod.setBody("{}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzZJJClass.writeFile("C:\\Users\\123456\\Desktop\\");
        }
        catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }

    /**
     * 修改PDF jar包里面的校验
     */
    public static  void modifyPDFJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("D:\\aspose-pdf-23.2.jar");
            //获取指定的class文件对象
            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.pdf.l10k");
            //从class对象中解析获取所有方法
            CtMethod[] methodA = zzZJJClass.getDeclaredMethods();
            for (CtMethod ctMethod : methodA) {
                //获取方法获取参数类型
                CtClass[] ps = ctMethod.getParameterTypes();
                //筛选同名方法，入参是Document
                if (ps.length == 1 && ctMethod.getName().equals("lI") && ps[0].getName().equals("java.io.InputStream")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    //替换指定方法的方法体
                    ctMethod.setBody("{lI(this);com.aspose.pdf.internal.imaging.internal.p71.Helper.help1();this.l0v = com.aspose.pdf.l11if.lf;lI=true;}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzZJJClass.writeFile("C:\\Users\\123456\\Desktop\\");
        }
        catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }
    public static void modifyExcelJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("D:\\aspose-cells-23.2.jar");
            //获取指定的class文件对象
//            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.cells.License");
            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.cells.p0h");
            //从class对象中解析获取所有方法
            CtMethod[] methodA = zzZJJClass.getDeclaredMethods();
            for (CtMethod ctMethod : methodA) {
                //获取方法获取参数类型
                CtClass[] ps = ctMethod.getParameterTypes();
                //筛选同名方法，入参是Document
                if (ps.length == 1 && ctMethod.getName().equals("a") && ps[0].getName().equals("org.w3c.dom.Document")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    //替换指定方法的方法体
//                    ctMethod.setBody("{a = this;com.aspose.cells.zblc.a();}");
                    ctMethod.setBody("{a = this;com.aspose.cells.r84.a();}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzZJJClass.writeFile("C:\\Users\\123456\\Desktop\\");

        } catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }
    @Test
    public void modifyWordsJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("D:\\aspose-words-23.5-jdk17.jar");
            //获取指定的class文件对象
            CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzKH");
            //从class对象中解析获取指定的方法
            CtMethod[] methodA = zzZJJClass.getDeclaredMethods("zzYEV");
            //遍历重载的方法
            for (CtMethod ctMethod : methodA) {
                CtClass[] ps = ctMethod.getParameterTypes();
                if (ctMethod.getName().equals("zzYEV")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    //替换指定方法的方法体
//                    ctMethod.setBody("{this.zzZ3l = new java.util.Date(Long.MAX_VALUE);this.zzWSL = com.aspose.words.zzYeQ.zzXgr;zzWiV = this;}");
                    ctMethod.setBody("{this.zzY2p = new java.util.Date(Long.MAX_VALUE);this.zzXDf = com.aspose.words.zzYiF.zzZik;zzVQN = this;}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzZJJClass.writeFile("D:\\maven\\aspose-words\\");

            //获取指定的class文件对象
            CtClass zzZJJClassB = ClassPool.getDefault().getCtClass("com.aspose.words.zzM9");
            //从class对象中解析获取指定的方法
            CtMethod methodB = zzZJJClassB.getDeclaredMethod("zzXLz");
            //替换指定方法的方法体
            methodB.setBody("{return 256;}");
            //这一步就是将破译完的代码放在桌面上
            zzZJJClassB.writeFile("D:\\maven\\aspose-words\\");
        } catch (Exception e) {
            System.out.println("错误==" + e);
        }

    }
}
