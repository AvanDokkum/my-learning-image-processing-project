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
 * 4: Locate the name(or location) + date data and put it in array.
 *
 * : sort the array on dates from oldest to newest.
 *
 * : copy images into the new folder
 *
 * : images are copied and sorted on dates.
 * */

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
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

        //3:
        List<Path> imageFiles = loadImages();

        //4:
        int amountOfImages = imageFiles.size();
        String[] metadata = new String[amountOfImages];
        for (int i = 0; i < amountOfImages; i++ ){
            BasicFileAttributes attributes = Files.readAttributes(imageFiles.get(i), BasicFileAttributes.class);
            System.out.println(attributes);
            System.out.println("File Name: " + imageFiles.get(i).getFileName());
            System.out.println("Creation Date: " + attributes.creationTime());
            System.out.println("Last Modified Date: " + attributes.lastModifiedTime());
        }




//        for (File image : imageFiles){
//            ImageInputStream imageInputStream = ImageIO.createImageInputStream(image);
//            imageInputStream.close();
//            System.out.println(imageInputStream);
//            //close the inputstream to prevent null? - 'ImageInputStream' used without 'try'-with-resources statement
//            //keeeeeep going!
//        }






//            ImageInputStream image = ImageIO.createImageInputStream(loadImages().get(0));
//            System.out.println(image);


//        File file = new File("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images\\fred5.jpg");
//        Path sourceFolder = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
//        try (Stream<Path> files = Files.list(sourceFolder)) {
//            List<Path> imageList = new ArrayList<>();
//            files.forEach(file -> imageList.add(file)); // same as imageList::add
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try (ImageInputStream input = ImageIO.createImageInputStream(loadImages())) {
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
//
//            if (!readers.hasNext()) {
//                throw new IOException("No image readers found for the format");
//            }
//
//            System.out.println(readers.next());
//            System.out.println(readers.next());
//            System.out.println(readers.next());

//            reader.setInput(input);
//
//            int width = reader.getWidth(0);  // Get image width
//            int height = reader.getHeight(0); // Get image height
//
//            System.out.println("Width: " + width + ", Height: " + height);
//            reader.dispose();

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static List<Path> loadImages() throws IOException {
        Path sourceFolder = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");

        // add try catch?
        List<Path> imageFiles = Files.list(sourceFolder)
//                .map(Path::toFile)
                .toList();


        System.out.println("images loaded " + imageFiles);
        return imageFiles;
    }
}