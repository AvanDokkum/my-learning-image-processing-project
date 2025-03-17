package com.webcanis;

import java.util.List;

public class Application {
    public static void main(String[] args){

//        ImageService imageService = new ImageService();
//        imageService.createOutputFolder();
//        imageService.readImagesRefactor();

        List<ImageDynamicObject> images = new ImageService().readImagesRefactor();
        for (ImageDynamicObject image : images) {
//            System.out.println(image);
            System.out.println(image.getField("File Name: "));
            System.out.println(image.getField("Creation Date: "));
        }

    }
}

