package com.webcanis;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageService {

    public int IMAGES_AMOUNT = 0;

    public Path directoryPath() {
        return Paths.get("C:\\Users\\desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
    }

    public void createOutputFolder() {
        String outputFolder = "output_images";
        Path outputFolderPath = Paths.get(directoryPath() + File.separator + outputFolder);
        System.out.println(outputFolderPath);
        try {
            Files.createDirectories(outputFolderPath);
            System.out.println("Folder created: " + outputFolderPath);
        } catch (Exception e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }
    }

    @Deprecated
    public List<Path> loadFiles() {
        List<Path> imageFiles = new ArrayList<>();  // Initialize to avoid null issues

        try (Stream<Path> paths = Files.list(directoryPath())) {
            imageFiles = paths.toList();
        } catch (IOException e) {
            System.out.println("Images couldn't be loaded.");
        }
        System.out.println("images loaded " + imageFiles);
        return imageFiles;
    }

    public List<Path> loadFilesRefactor() {
        List<Path> imageFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(directoryPath())) {
            imageFiles = paths.filter(Files::isRegularFile)
                    .filter(path -> ImageFileChecker.ImageType.matches(path.getFileName().toString()))
//                    .forEach(System.out::println)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFiles;
    }

    @Deprecated
    public boolean isImageFile(File file) {
        List<String> imageExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp");
        String fileName = file.getName().toLowerCase();
        return imageExtensions.stream().anyMatch(fileName::endsWith);
    }

    @Deprecated
    public void readImages() {
        for (Path imagePath : loadFiles()) {
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
                    System.out.println("Total amount of images: " + ++IMAGES_AMOUNT);

                } catch (IOException e) {
                    System.out.println("Couldn't read file attributes from: " + fileName);
                }
            }

        }
    }

    @Deprecated
    public List<ImageMetaData> readImagesRefactor() {

        List<ImageMetaData> imageList = new ArrayList<>();

        for (Path imagePath : loadFilesRefactor()) {
            File file = imagePath.toFile();
//            if (isImageFile(file)) {
            String fileName = file.getName();
            try {

                System.out.println("FILES API Metadata:");
                BasicFileAttributes attributes = Files.readAttributes(imagePath, BasicFileAttributes.class);
                System.out.println("File Name: " + fileName);
                System.out.println("Creation Date: " + attributes.creationTime());
                System.out.println("Last Modified Date: " + attributes.lastModifiedTime());
                System.out.println("Last Access Time: " + attributes.lastAccessTime());
                System.out.println("Attribute Size: " + attributes.size());
                System.out.println("---");

                DrewMetaDataExtractor.Extract(file);
                System.out.println("---\n");

                ImageMetaData imageMetadata = new ImageMetaData();
                imageMetadata.setField("File Name: ", fileName);
                imageMetadata.setField("Creation Date: ", attributes.creationTime());
                imageList.add(imageMetadata);

                ++IMAGES_AMOUNT;

            } catch (IOException e) {
                System.out.println("Couldn't read file attributes from: " + fileName);
            }
//            }
        }
        System.out.println("Total amount of images: " + IMAGES_AMOUNT);
        return imageList;
    }

    public List<ImageMetaData> readMetaData() {
        List<ImageMetaData> imageMetaDataList = new ArrayList<>();
        for (Path imagePath : loadFilesRefactor()) {
            try {
                BasicFileAttributes attributes = Files.readAttributes(imagePath, BasicFileAttributes.class);
                ImageMetaData imageMetaData = new ImageMetaData();
                imageMetaData.setField("Attribute Size", attributes.size());
                imageMetaData.setField("Attribute Creation Time", attributes.creationTime());

                Metadata exifMetadata = ImageMetadataReader.readMetadata(imagePath.toFile());
                if (exifMetadata != null && exifMetadata.getDirectories().iterator().hasNext()) {
                    for (Directory directory : exifMetadata.getDirectories()) {
                        for (Tag tag : directory.getTags()) {
                            if (tag.getTagName().equals("User Comment")) {
                                continue;
                            }
                            imageMetaData.setField(tag.getTagName(), tag.getDescription());
                        }
                    }
                }
                imageMetaDataList.add(imageMetaData);

            } catch (ImageProcessingException | IOException error) {
                System.out.println("Couldn't read file attributes from: " + imagePath.toFile().getName());
                error.printStackTrace();
            }
        }
        return imageMetaDataList;
    }
}

