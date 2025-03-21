package com.webcanis;

import java.io.File;
import java.util.stream.Stream;

public class ImageFileChecker {

    //refactor: remove class and just use the enum

    public enum ImageType {
//        JSON(".json"), //contains metadata from image with the same name + file type.

//        SVG Image can NOT be read by DrewMetaDataExtractor = Error reading EXIF data: File format could not be determined - null
//        SVG(".svg"),
        JPG(".jpg"),
        JPEG(".jpeg"),
        PNG(".png"),
        GIF(".gif"),
        BMP(".bmp"),
        WEBP(".webp"),
        TIFF(".tiff"),
        TIF(".TIF"),
        AVIF(".avif"),
        ICON(".ico")
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
