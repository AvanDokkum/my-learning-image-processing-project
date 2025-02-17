package com.webcanis;

/*
 * Pseudocode image sorting on date and copy images to new folder
 *
 * 1: locate the images input folder
 * 1-rf: refactor into a scanner or some UI input
 *
 * 2: create a new folder for the output images
 *
 * 3: check folder for a GIF/PNG/JPEG (.jpg) file and locate the name(or location) + date data.
 * 3rf: check folder for all .jpg files and put the name(or location) + date data in an array.
 *
 * : sort the array on dates from oldest to newest.
 *
 * : copy images into the new folder
 * : images are copied and sorted on dates.
 * */

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        List<File> imageFiles = loadImages();
        for (File image : imageFiles){
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(image);
            //close the inputstream to prevent null? - 'ImageInputStream' used without 'try'-with-resources statement
            //keeeeeep going!
        }

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

    private static List<File> loadImages() throws IOException {
        Path sourceFolder = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");

        List<File> imageFiles = Files.list(sourceFolder)
                .map(Path::toFile)
                .toList();
        // add try catch?

        System.out.println("images loaded " + imageFiles);
        return imageFiles;
    }
}