package com.webcanis;

/*
* Pseudocode image sorting on date and copy images to new folder
* 1: locate the images input folder
* 1-rf: refactor into a scanner
* 2: create a new folder for the output images
* : check folder for all .jpg files and put the name(or location) + date data in an array.
* : sort the array on dates from oldest to newest.
*
* : copy images into the new folder
* : images are copied and sorted on dates.
* */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {
        //1:locate the images input folder
        Path inputPath = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
        String outputFolder = "output_images";

        //2:create a new folder for the output images
        Path outputFolderPath = Paths.get(inputPath + File.separator + outputFolder);
        try {
            Files.createDirectories(outputFolderPath);
            System.out.println("Folder created: " + outputFolderPath);
        } catch (Exception e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }

        //read single image:
//        File singleFile = new File("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images\\fred 5 \u200ESeptember \u200E2012.jpg");
//        System.out.println(singleFile);




//        String inputFolder = Main.class.getClassLoader().getResource("input_images").getPath();
//        System.out.println("inputFolder outputPath: \"" + inputFolder + "\"");



    }
}