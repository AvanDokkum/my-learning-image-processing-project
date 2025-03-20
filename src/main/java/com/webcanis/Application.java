package com.webcanis;

import java.util.List;

public class Application {
    public static void main(String[] args) {

        ImageService imageService = new ImageService();
        imageService.createOutputFolder();
//        imageService.readImagesRefactor();

        List<ImageMetaData> images = imageService.readMetaData();
        for (ImageMetaData image : images) {
            System.out.println(image);
        }

        System.out.println("Total amount of images: " + ImageService.IMAGES_AMOUNT);

        imageService.removeDoubles(images);



        //pseudocode:
        //think about how to order images on date; What if date data isn't there, or is incomplete?
        //order List<ImageMetaData> based on date using a comparable?
        //export list image data to .json?
        //copy images to output folder sorted on date.
    }

}


