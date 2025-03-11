package com.webcanis;

public class Application {
    public static void main(String[] args){
        ImageService imageService = new ImageService();
        imageService.createOutputFolder();
        imageService.readImagesRefactor();
    }



}

