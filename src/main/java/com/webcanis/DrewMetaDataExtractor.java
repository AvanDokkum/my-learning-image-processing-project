package com.webcanis;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;

import java.io.File;

public class DrewMetaDataExtractor {
    public static void Extract(File imageFile) {
        try {
            // Read metadata
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

            // Check if metadata exists
            if (metadata != null && metadata.getDirectories().iterator().hasNext()) {
                System.out.println("📸 EXIF Metadata:");

                // Iterate through metadata directories
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        System.out.println(tag);
                    }
                }
            } else {
                System.out.println("❌ No EXIF metadata found.");
            }
        } catch (Exception e) {
            System.err.println("Error reading EXIF data: " + e.getMessage());
        }
    }
}
