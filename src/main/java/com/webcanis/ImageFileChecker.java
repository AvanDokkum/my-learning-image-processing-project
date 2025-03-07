package com.webcanis;

import java.io.File;
import java.util.stream.Stream;

public class ImageFileChecker {

    public enum ImageType {
        JPG(".jpg"),
        JPEG(".jpeg"),
        PNG(".png"),
        GIF(".gif"),
        BMP(".bmp"),
        WEBP(".webp"),
        SVG(".svg"),
        TIFF(".tiff"),
        TIF(".TIF"),
        AVIF(".avif")
        ;

        private final String extension;

        ImageType(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }

//        public static boolean isSupportedExtension(String extension) {
//            for (ImageType type : ImageType.values()) {
//                if (type.getExtension().equalsIgnoreCase(extension)) {
//                    return true;
//                }
//            }
//            return false;
//        }

        public static boolean matches(String fileName) {
            return Stream.of(values()).anyMatch(ext -> fileName.endsWith(ext.getExtension()));
        }
    }

//    public boolean isImageFile(File file) {
//        String fileName = file.getName().toLowerCase();
//        for (ImageType type : ImageType.values()) {
//            if (fileName.endsWith(type.getExtension())) {
//                return true;
//            }
//        }
//        return false;
//    }

//    //Alternative using the enum static method and stream
//    public boolean isImageFileAlt(File file) {
//        String fileName = file.getName().toLowerCase();
//        return Arrays.stream(ImageType.values()).anyMatch(type -> fileName.endsWith(type.getExtension()));
//    }

}
