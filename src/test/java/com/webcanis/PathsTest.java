package com.webcanis;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class PathsTest {

    @Test
    public void inputImagesExists() throws Exception {
        Path path = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
        assertTrue(Files.exists(path));

        path = Paths.get("C:/Users/Desktop/IdeaProjects/my-learning-image-processing-project/src/main/resources/input_images");
        assertTrue(Files.exists(path));

        path = Paths.get("C:","Users", "Desktop", "IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images");
        assertTrue(Files.exists(path));

        path = Paths.get("C:","Users", "Desktop", "IdeaProjects").resolve("my-learning-image-processing-project", "src\\main\\resources\\input_images");
        assertTrue(Files.exists(path));
    }

    @Test
    public void outputFolderExists() throws Exception {
        Path path = Paths.get("C:\\Users\\Desktop\\IdeaProjects\\my-learning-image-processing-project\\src\\main\\resources\\input_images\\output_images");
        assertTrue(Files.exists(path));
    }

}
