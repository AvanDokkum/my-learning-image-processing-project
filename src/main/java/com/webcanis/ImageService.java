package com.webcanis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ImageService {

    public static final int IMAGES_AMOUNT = loadImages().size();

    private static Path directoryPath() {
        return Paths.get("C:\\Users\\arjan\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
    }

    public void createOutputFolder() {
        String outputFolder = "output_images";
        Path outputFolderPath = Paths.get(directoryPath() + File.separator + outputFolder);
        try {
            Files.createDirectories(outputFolderPath);
            System.out.println("Folder created: " + outputFolderPath);
        } catch (Exception e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }
    }

    public static List<Path> loadImages() {
        List<Path> imageFiles = new ArrayList<>();  // Initialize to avoid null issues
        try (Stream<Path> paths = Files.list(directoryPath())) {
            imageFiles = paths.toList();
        } catch (IOException e) {
            System.out.println("Images couldn't be loaded.");
        }
        System.out.println("images loaded " + imageFiles);
        return imageFiles;
    }

    public static boolean isImageFile(File file) {
        List<String> imageExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp");
        String fileName = file.getName().toLowerCase();
        return imageExtensions.stream().anyMatch(fileName::endsWith);
    }

    public void readImages() {
        String[] imageData = new String[IMAGES_AMOUNT];
        for (Path imagePath : loadImages()) {
            File file = imagePath.toFile();
            if (isImageFile(file)) {
                String fileName = file.getName();
                try {
                    BasicFileAttributes attributes = Files.readAttributes(imagePath, BasicFileAttributes.class);
                    System.out.println(attributes);
                    System.out.println("File Name: " + fileName);
                    System.out.println("Creation Date: " + attributes.creationTime());
                    System.out.println("Last Modified Date: " + attributes.lastModifiedTime());
                    System.out.println("Last Access Time: " + attributes.lastAccessTime());
                    System.out.println("Attribute Size: " + attributes.size());
                    System.out.println("\n");

                    DrewMetaDataExtractor.Extract(file);

                } catch (IOException e) {
                    System.out.println("Couldn't read file attributes from: " + fileName);
                }
            }

        }
    }
}

