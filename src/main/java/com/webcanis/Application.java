package com.webcanis;

import java.util.List;

public class Application {
    public static void main(String[] args) {

//        ImageService imageService = new ImageService();
//        imageService.createOutputFolder();
//        imageService.readImagesRefactor();

        List<ImageMetaData> images = new ImageService().readMetaData();
        for (ImageMetaData image : images) {
            System.out.println(image);
        }
    }

}


