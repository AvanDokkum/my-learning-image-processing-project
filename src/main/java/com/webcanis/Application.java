package com.webcanis;

/*
 * Pseudocode image sorting on date and copy images to new folder
 *
 * 1: locate the images input folder
 * 1-rf: refactor into a scanner or some UI input
 *
 * 2: create a new folder for the output images
 *
 * 3: read all images in the folder
 *
 * 4: Locate the name(or location) + date data and put it in an object array.
 *
 * : sort the array on dates from oldest to newest.
 *
 * : copy images into the new folder
 *
 * : images are copied and sorted on dates.
 * */

import java.sql.SQLOutput;

public class Application {
    public static void main(String[] args){
        System.out.println("Amount of images: " + ImageService.IMAGES_AMOUNT);
        ImageService imageService = new ImageService();
//        imageService.createOutputFolder();
        imageService.readImages();

//        //1:locate the images input folder
//        Path inputPath = Paths.get("C:\\Users\\arjan\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
//        String outputFolder = "output_images";
//
//        //2:create a new folder for the output images
//        Path outputFolderPath = Paths.get(inputPath + File.separator + outputFolder);
//        try {
//            Files.createDirectories(outputFolderPath);
//            System.out.println("Folder created: " + outputFolderPath);
//        } catch (Exception e) {
//            System.err.println("Failed to create folder: " + e.getMessage());
//        }




//        //3:
//        List<Path> imageFiles = loadImages();

//        //4:
//        int amountOfImages = imageFiles.size();
//        String[] imageData = new String[amountOfImages];
//
//        for (int i = 0; i < amountOfImages; i++ ){
//            imagePath = imageFiles.get(i);
//            File imageFile = imageFiles.get(i).toFile();
//
//            if (isImageFile(imageFile)) {
//                BasicFileAttributes attributes = Files.readAttributes(imagePath, BasicFileAttributes.class);
//                System.out.println("\n");
//                System.out.println(attributes);
//                System.out.println("File Name: " + imageFiles.get(i).getFileName());
//                System.out.println("Creation Date: " + attributes.creationTime());
//                System.out.println("Last Modified Date: " + attributes.lastModifiedTime());
//                System.out.println("Last Access Time: " + attributes.lastAccessTime());
//                System.out.println("Attribute Size: " + attributes.size());
////            attributes.fileKey();
//                System.out.println("\n");
//
//                DrewMetaDataExtractor.Extract(imageFile);
//            }
//            //todo: create an object from the name and attributes I need, put it in a list and sort it.
//        }
    }



}

