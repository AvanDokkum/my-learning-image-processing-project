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

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        //1:locate the images input folder
        Path inputPath = Paths.get("C:\\Users\\arjan\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
        String outputFolder = "output_images";

        //2:create a new folder for the output images
        Path outputFolderPath = Paths.get(inputPath + File.separator + outputFolder);
        try {
            Files.createDirectories(outputFolderPath);
            System.out.println("Folder created: " + outputFolderPath);
        } catch (Exception e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }

        //3:
        List<Path> imageFiles = loadImages();

        //4:
        int amountOfImages = imageFiles.size();
        String[] imageData = new String[amountOfImages];

        for (int i = 0; i < amountOfImages; i++ ){
            Path imagePath = imageFiles.get(i);
            File imageFile = imageFiles.get(i).toFile();

            if (isImageFile(imageFile)) {
                BasicFileAttributes attributes = Files.readAttributes(imagePath, BasicFileAttributes.class);
                System.out.println("\n");
                System.out.println(attributes);
                System.out.println("File Name: " + imageFiles.get(i).getFileName());
                System.out.println("Creation Date: " + attributes.creationTime());
                System.out.println("Last Modified Date: " + attributes.lastModifiedTime());
                System.out.println("Last Access Time: " + attributes.lastAccessTime());
                System.out.println("Attribute Size: " + attributes.size());
//            attributes.fileKey();
                System.out.println("\n");

                //Apache commons ImageMetadata
//                try {
//                    // Read EXIF metadata
//                    ImageMetadata metadata = Imaging.getMetadata(imageFile);
//
//                    // Check if metadata exists
//                    if (metadata != null) {
//                        System.out.println("📸 EXIF Metadata:");
//                        System.out.println(metadata.toString()); // Print all metadata
//                    } else {
//                        System.out.println("❌ No EXIF metadata found.");
//                    }
//
//                } catch (IOException | ImageReadException e) {
//                    System.err.println("Error reading EXIF data: " + e.getMessage());
//                }

                // DrewMetaDataExtractor
//                try {
//                    // Read metadata
//                    Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
//
//                    // Check if metadata exists
//                    if (metadata != null && metadata.getDirectories().iterator().hasNext()) {
//                        System.out.println("📸 EXIF Metadata:");
//
//                        // Iterate through metadata directories
//                        for (Directory directory : metadata.getDirectories()) {
//                            for (Tag tag : directory.getTags()) {
//                                System.out.println(tag);
//                            }
//                        }
//                    } else {
//                        System.out.println("❌ No EXIF metadata found.");
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("Error reading EXIF data: " + e.getMessage());
//                }

                DrewMetaDataExtractor.Extract(imageFile);
            }
            //todo: create an object from the name and attributes I need, put it in a list and sort it.
        }
    }

    private static List<Path> loadImages() throws IOException {
        Path sourceFolder = Paths.get("C:\\Users\\arjan\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");

        // add try catch?
        List<Path> imageFiles = Files.list(sourceFolder)
//                .map(Path::toFile)
                .toList();

        System.out.println("images loaded " + imageFiles);
        return imageFiles;
    }

    public static boolean isImageFile(File file) {
        List<String> imageExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp");
        String fileName = file.getName().toLowerCase();

        return imageExtensions.stream().anyMatch(fileName::endsWith);
    }

}

