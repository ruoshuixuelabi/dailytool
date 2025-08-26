package com.xuexi.dailytool;

import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.sl.usermodel.PictureData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ppt {
    public static void main(String[] args) throws IOException {
        HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl("d://ceshi ppt的情况.ppt"));

        // Add a new picture to this slideshow
        HSLFPictureData pd = ppt.addPicture(new File("d://QQ图片20250114155025.png"), PictureData.PictureType.PNG);

        // Traverse all slides and add the picture to each slide
        for (HSLFSlide slide : ppt.getSlides()) {
            HSLFPictureShape pictNew = new HSLFPictureShape(pd);

            // Set the position of the image in the slide
            pictNew.setAnchor(new java.awt.Rectangle(100, 100, 300, 200));

            // Add the picture to the slide
            slide.addShape(pictNew);
        }

        // Save the modified slide show as a new file
        FileOutputStream out = new FileOutputStream("d://ppt添加图片02.ppt");
        ppt.write(out);
        out.close();
    }
}
