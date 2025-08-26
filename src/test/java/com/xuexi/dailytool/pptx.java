package com.xuexi.dailytool;

import org.apache.commons.io.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class pptx {
    public static void main(String[] args) throws IOException {
        // Load an existing PPTX file
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("d://部门管理培训-20250109.pptx"));
        // Read the picture data from the file
        byte[] pictureData = IOUtils.toByteArray(new FileInputStream("d://QQ图片20250114155025.png"));
        XSLFPictureData pd = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
        // Traverse all slides and add the picture to each slide
        for (XSLFSlide slide : ppt.getSlides()) {
            XSLFPictureShape pic = slide.createPicture(pd);
            // Set the position of the image in the slide
            pic.setAnchor(new java.awt.Rectangle(100, 100, 300, 200));
        }
        // Save the modified slide show as a new file
        try (FileOutputStream out = new FileOutputStream("d://pptx添加图片02.pptx")) {
            ppt.write(out);
        }
    }
}
