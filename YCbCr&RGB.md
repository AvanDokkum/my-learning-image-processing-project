### **YCbCr vs. RGB: Practical Understanding**
#### **1. The Core Difference**
- **RGB (Red, Green, Blue):** This is an additive color model where colors are represented by mixing red, green, and blue light intensities. It is commonly used for display screens.
- **YCbCr (Luminance, Chrominance-Blue, Chrominance-Red):** This is a color space designed for **efficient storage and transmission**. It separates brightness (**Y**) from color information (**Cb, Cr**), which allows for **compression and bandwidth reduction** (used in JPEG, video streaming, etc.).

The key advantage of YCbCr is that the human eye is more sensitive to brightness than to color details. This allows **chroma subsampling**, where color information is stored at a lower resolution than brightness, reducing data size without a major loss in quality.

---

## **2. Practical Uses in a Backend Application**
In a backend context, YCbCr is relevant in:
1. **Image Processing Pipelines (JPEG decoding, video transcoding)**
2. **Computer Vision (Pre-processing for machine learning)**
3. **Media Streaming (Reducing bandwidth for video delivery)**

---

## **3. Simple Example: Converting RGB to YCbCr**
Let's say you're handling image uploads in a backend service and need to convert RGB images to YCbCr.

### **Java Implementation**
Using **Java's BufferedImage** and basic math:
```java
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RGBToYCbCr {
    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("input.jpg"));
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Convert RGB to YCbCr
                int Y  = (int) ( 0.299 * r + 0.587 * g + 0.114 * b);
                int Cb = (int) (-0.1687 * r - 0.3313 * g + 0.5 * b + 128);
                int Cr = (int) ( 0.5 * r - 0.4187 * g - 0.0813 * b + 128);

                System.out.printf("Pixel (%d,%d) - Y: %d, Cb: %d, Cr: %d%n", x, y, Y, Cb, Cr);
            }
        }
    }
}
```
### **Key Takeaways**
- **Y represents brightness** (grayscale value).
- **Cb and Cr store color differences**.
- This separation allows **efficient compression** (Y can be high-res, CbCr low-res).

---

## **4. Advanced Example: Optimized Video Encoding Pipeline**
In a real-world backend, converting images to YCbCr is often used in **video encoding (e.g., H.264, WebP, JPEG compression)**.

### **Using OpenCV in Java for Efficient Conversion**
Instead of manually computing values, we can use **OpenCV (JavaCV wrapper)**:
```java
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;

public class YCbCrConverter {
    public static void main(String[] args) {
        Mat rgbImage = opencv_imgcodecs.imread("input.jpg");

        // Convert RGB to YCbCr
        Mat ycbcrImage = new Mat();
        opencv_imgproc.cvtColor(rgbImage, ycbcrImage, opencv_imgproc.COLOR_BGR2YCrCb);

        // Save the output
        opencv_imgcodecs.imwrite("output_ycbcr.jpg", ycbcrImage);
        System.out.println("YCbCr image saved successfully.");
    }
}
```
### **Why Use OpenCV?**
- **Faster processing** with optimized matrix operations.
- **Built-in chroma subsampling**, useful for **JPEG compression**.
- **Better scalability** for large image processing in backend applications.

---

## **5. Best Practices**
1. **Use YCbCr for Compression:**
    - Store images in YCbCr format if saving space is a priority (e.g., converting images before storing in cloud storage).
    - Use **JPEG/WebP** instead of raw RGB images.

2. **Chroma Subsampling (4:2:0 or 4:2:2):**
    - If you process video, reduce color detail but maintain brightness.
    - Works well in streaming applications where bandwidth is limited.

3. **Use GPU-accelerated libraries for large-scale conversion:**
    - OpenCV (with CUDA) or **FFmpeg** for video processing.
    - JavaCV if integrating OpenCV in Java-based media applications.

---

## **6. Conclusion**
- **RGB is ideal for display**, while **YCbCr is optimized for storage & transmission**.
- **Simple conversion** can be done using math, but **OpenCV or FFmpeg** is better for production.
- **YCbCr compression (e.g., chroma subsampling)** is a standard in **media processing**.




Would you like a **Spring Boot example** for an image-processing microservice? 🚀
Here’s a **Spring Boot** microservice that accepts an image upload, converts it to **YCbCr**, and returns the processed image.

---

## **1. Project Setup**
### **Dependencies (`pom.xml`)**
```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- OpenCV via JavaCV -->
    <dependency>
        <groupId>org.bytedeco</groupId>
        <artifactId>javacv-platform</artifactId>
        <version>1.5.9</version>
    </dependency>

    <!-- Image Handling -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.11.0</version>
    </dependency>
</dependencies>
```

---

## **2. Spring Boot Controller (`ImageProcessingController.java`)**
This REST controller:
1. Accepts an image file.
2. Converts it from **RGB to YCbCr**.
3. Returns the processed image.

```java
package com.example.imageprocessing;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api")
public class ImageProcessingController {

    @PostMapping(value = "/convert-ycbcr", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] convertImageToYCbCr(@RequestParam("file") MultipartFile file) throws IOException {
        // Convert MultipartFile to OpenCV Mat
        File tempFile = File.createTempFile("upload", ".jpg");
        file.transferTo(tempFile);
        Mat rgbImage = opencv_imgcodecs.imread(tempFile.getAbsolutePath());

        if (rgbImage.empty()) {
            throw new IOException("Invalid image format");
        }

        // Convert to YCbCr
        Mat ycbcrImage = new Mat();
        opencv_imgproc.cvtColor(rgbImage, ycbcrImage, opencv_imgproc.COLOR_BGR2YCrCb);

        // Save processed image to byte array
        File outputFile = File.createTempFile("output", ".jpg");
        opencv_imgcodecs.imwrite(outputFile.getAbsolutePath(), ycbcrImage);

        return readFileToByteArray(outputFile);
    }

    private byte[] readFileToByteArray(File file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        }
    }
}
```

---

## **3. Testing the API**
You can test the service using **cURL** or Postman:

### **cURL Command**
```sh
curl -X POST -F "file=@input.jpg" http://localhost:8080/api/convert-ycbcr --output output_ycbcr.jpg
```
This sends an **RGB image (`input.jpg`)** and downloads the **YCbCr-converted image (`output_ycbcr.jpg`)**.

---

## **4. Best Practices for Production**
1. **Optimize Image Processing**
    - Use `opencv_core.CV_8UC3` for faster operations.
    - If handling video, consider **FFmpeg (JavaCV has bindings).**

2. **Deploy as a Scalable Service**
    - Use **Docker** to package OpenCV with the app.
    - Deploy on **AWS Lambda** (if latency is acceptable) or **Kubernetes** for high throughput.

3. **Enable Caching (Redis)**
    - If processing large images repeatedly, cache results.

---

## **Conclusion**
This **Spring Boot** microservice provides **on-the-fly RGB to YCbCr conversion**. 


Would you like a **Docker setup** or **database integration** for processed images? 🚀

### **Enhancing the Spring Boot Image Processing Service**
We’ll now:
1. **Dockerize** the Spring Boot app.
2. **Store processed images in a database** (PostgreSQL).
3. **Add a retrieval API to fetch converted images.**

---

## **1. Docker Setup**
### **Dockerfile**
Create a `Dockerfile` in the project root:

```dockerfile
# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built application JAR
COPY target/image-processing-app.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### **Build & Run**
```sh
# Build the JAR (Ensure the project is built)
mvn clean package -DskipTests

# Build the Docker image
docker build -t image-processing-app .

# Run the container
docker run -p 8080:8080 image-processing-app
```

---

## **2. PostgreSQL Integration for Storing Images**
### **Add Dependency (`pom.xml`)**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### **Docker-Compose (PostgreSQL)**
Create a `docker-compose.yml` file:

```yaml
version: '3.8'
services:
  db:
    image: postgres:15
    restart: always
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: secret
      POSTGRES_DB: image_db
    ports:
      - "5432:5432"
    volumes:
      - pg_data:/var/lib/postgresql/data

volumes:
  pg_data:
```

Run:
```sh
docker-compose up -d
```

---

## **3. Define Database Entity (`ImageEntity.java`)**
```java
package com.example.imageprocessing.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] data;
}
```

---

## **4. Image Repository (`ImageRepository.java`)**
```java
package com.example.imageprocessing.repository;

import com.example.imageprocessing.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
```

---

## **5. Update Controller to Save & Retrieve Images**
```java
package com.example.imageprocessing.controller;

import com.example.imageprocessing.model.ImageEntity;
import com.example.imageprocessing.repository.ImageRepository;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api")
public class ImageProcessingController {

    private final ImageRepository imageRepository;

    public ImageProcessingController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/convert-ycbcr")
    public String convertAndSaveImage(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload", ".jpg");
        file.transferTo(tempFile);
        Mat rgbImage = opencv_imgcodecs.imread(tempFile.getAbsolutePath());

        if (rgbImage.empty()) {
            throw new IOException("Invalid image format");
        }

        Mat ycbcrImage = new Mat();
        opencv_imgproc.cvtColor(rgbImage, ycbcrImage, opencv_imgproc.COLOR_BGR2YCrCb);

        File outputFile = File.createTempFile("output", ".jpg");
        opencv_imgcodecs.imwrite(outputFile.getAbsolutePath(), ycbcrImage);

        byte[] imageData = readFileToByteArray(outputFile);
        
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFilename(file.getOriginalFilename());
        imageEntity.setData(imageData);
        imageRepository.save(imageEntity);

        return "Image successfully converted and stored. ID: " + imageEntity.getId();
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"))
                .getData();
    }

    private byte[] readFileToByteArray(File file) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        }
    }
}
```

---

## **6. Test the API**
### **Convert & Store an Image**
```sh
curl -X POST -F "file=@input.jpg" http://localhost:8080/api/convert-ycbcr
```
Response:
```json
Image successfully converted and stored. ID: 1
```

### **Retrieve the Image**
```sh
curl -X GET http://localhost:8080/api/images/1 --output output_ycbcr.jpg
```

---

## **7. Best Practices**
1. **Optimize Database Storage**
    - Store images in **cloud storage (S3, MinIO)** instead of a database for large-scale apps.
    - Save only **metadata and URLs** in the database.

2. **Implement Rate Limiting (Spring Security)**
    - Prevent abuse by limiting upload requests.

3. **Enable Compression**
    - Use **WebP format** for better compression and quality.

---

## **Final Thoughts**
Now, we have a **Spring Boot microservice** that:
- Accepts an image via **REST API**.
- Converts it from **RGB to YCbCr**.
- Stores it in **PostgreSQL**.
- Allows retrieval of processed images.



Would you like an **S3 storage integration** for better scalability? 🚀

### **Enhancing the Spring Boot Image Processing Service with AWS S3 Storage**
Now, instead of storing images directly in PostgreSQL, we will:
1. **Upload the YCbCr-processed images to AWS S3.**
2. **Store only metadata (filename, URL) in the database.**
3. **Retrieve the processed images via S3 URLs.**

---

## **1. Add AWS S3 Dependency (`pom.xml`)**
```xml
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
    <version>2.20.74</version>
</dependency>
```

---

## **2. Configure AWS Credentials**
Create a file at `~/.aws/credentials` (Linux/macOS) or `C:\Users\YourUser\.aws\credentials` (Windows):

```
[default]
aws_access_key_id=YOUR_AWS_ACCESS_KEY
aws_secret_access_key=YOUR_AWS_SECRET_KEY
```

---

## **3. Define S3 Properties (`application.yml`)**
```yaml
aws:
  s3:
    bucket-name: my-image-bucket
  region: us-east-1
```

---

## **4. Database Entity (`ImageMetadata.java`)**
Since the images will be stored in **S3**, we only save **metadata** (filename & S3 URL).

```java
package com.example.imageprocessing.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_metadata")
public class ImageMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String s3Url;
}
```

---

## **5. Repository (`ImageMetadataRepository.java`)**
```java
package com.example.imageprocessing.repository;

import com.example.imageprocessing.model.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
}
```

---

## **6. AWS S3 Service (`S3StorageService.java`)**
Handles the **uploading** of processed images to **S3**.

```java
package com.example.imageprocessing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName;

    public S3StorageService(@Value("${aws.s3.bucket-name}") String bucketName,
                            @Value("${aws.region}") String region) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        this.bucketName = bucketName;
    }

    public String uploadFile(File file, String key) throws Exception {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)  // Make public for easy access
                .build();

        s3Client.putObject(putRequest, RequestBody.fromFile(file));

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
```

---

## **7. Update Controller (`ImageProcessingController.java`)**
1. Convert **RGB → YCbCr**.
2. Upload the processed image to **S3**.
3. Store **S3 URL & metadata** in the database.

```java
package com.example.imageprocessing.controller;

import com.example.imageprocessing.model.ImageMetadata;
import com.example.imageprocessing.repository.ImageMetadataRepository;
import com.example.imageprocessing.service.S3StorageService;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageProcessingController {

    private final ImageMetadataRepository imageRepository;
    private final S3StorageService s3StorageService;

    public ImageProcessingController(ImageMetadataRepository imageRepository, S3StorageService s3StorageService) {
        this.imageRepository = imageRepository;
        this.s3StorageService = s3StorageService;
    }

    @PostMapping("/convert-ycbcr")
    public String convertAndUploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        // Convert MultipartFile to OpenCV Mat
        File tempFile = File.createTempFile("upload", ".jpg");
        file.transferTo(tempFile);
        Mat rgbImage = opencv_imgcodecs.imread(tempFile.getAbsolutePath());

        if (rgbImage.empty()) {
            throw new IOException("Invalid image format");
        }

        // Convert RGB to YCbCr
        Mat ycbcrImage = new Mat();
        opencv_imgproc.cvtColor(rgbImage, ycbcrImage, opencv_imgproc.COLOR_BGR2YCrCb);

        // Save locally before uploading
        File outputFile = File.createTempFile("output", ".jpg");
        opencv_imgcodecs.imwrite(outputFile.getAbsolutePath(), ycbcrImage);

        // Upload to S3
        String s3Key = "processed/" + file.getOriginalFilename();
        String s3Url = s3StorageService.uploadFile(outputFile, s3Key);

        // Store metadata in DB
        ImageMetadata metadata = new ImageMetadata();
        metadata.setFilename(file.getOriginalFilename());
        metadata.setS3Url(s3Url);
        imageRepository.save(metadata);

        return "Processed image stored at: " + s3Url;
    }

    @GetMapping("/images/{id}")
    public String getImage(@PathVariable Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"))
                .getS3Url();
    }
}
```

---

## **8. Testing the API**
### **Convert & Upload an Image**
```sh
curl -X POST -F "file=@input.jpg" http://localhost:8080/api/convert-ycbcr
```
Response:
```json
Processed image stored at: https://my-image-bucket.s3.amazonaws.com/processed/input.jpg
```

### **Retrieve Image URL**
```sh
curl -X GET http://localhost:8080/api/images/1
```
Response:
```json
"https://my-image-bucket.s3.amazonaws.com/processed/input.jpg"
```

---

## **9. Best Practices**
✅ **Use Presigned URLs** for temporary access if images should not be public.  
✅ **Store images in a CDN** (e.g., CloudFront) for faster access.  
✅ **Enable Automatic Expiration** for temporary images using **S3 lifecycle policies**.  
✅ **Use Multipart Uploads** for large images (if needed).

---

## **10. Final Thoughts**
🚀 Now we have:
- A **Spring Boot microservice** that **converts RGB → YCbCr**.
- **Stores images in AWS S3** instead of a database.
- **Serves images via S3 URLs**.




Would you like a **CDN integration (CloudFront)** for even faster delivery? 🚀

### **Enhancing the Image Processing Service with Amazon CloudFront (CDN)**
Now, we’ll optimize image delivery by:
1. **Using AWS CloudFront as a Content Delivery Network (CDN).**
2. **Serving images with a faster, globally distributed URL.**
3. **Ensuring private access via Signed URLs (optional).**

---

## **1. Set Up CloudFront Distribution**
### **1.1 Create a CloudFront Distribution for S3**
1. Open the **AWS Console** → Go to **CloudFront**.
2. Click **Create Distribution**.
3. Select **Origin Domain Name** → Choose your S3 bucket (`my-image-bucket`).
4. **Restrict Bucket Access** → **Yes**.
5. **Origin Access Control Settings (OAC)** → Create a new OAC.
6. **Cache Policy** → Set TTL (e.g., 86400s for 1-day cache).
7. Click **Create Distribution**.

**CloudFront will generate a URL** like:
```
https://d123abc.cloudfront.net/
```

---

## **2. Update S3 Bucket Policy**
Replace `d123abc.cloudfront.net` with your CloudFront URL.

1. Go to **S3 → my-image-bucket → Permissions**.
2. Update the **Bucket Policy**:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "cloudfront.amazonaws.com"
      },
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::my-image-bucket/*",
      "Condition": {
        "StringEquals": {
          "AWS:SourceArn": "arn:aws:cloudfront::YOUR_AWS_ACCOUNT_ID:distribution/DISTRIBUTION_ID"
        }
      }
    }
  ]
}
```

---

## **3. Update the Spring Boot Application**
Modify the `S3StorageService.java` to **replace the S3 URL with the CloudFront URL**.

```java
package com.example.imageprocessing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String cloudFrontUrl;

    public S3StorageService(@Value("${aws.s3.bucket-name}") String bucketName,
                            @Value("${aws.cloudfront.url}") String cloudFrontUrl,
                            @Value("${aws.region}") String region) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        this.bucketName = bucketName;
        this.cloudFrontUrl = cloudFrontUrl;
    }

    public String uploadFile(File file, String key) throws Exception {
        // Upload to S3
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl(ObjectCannedACL.PRIVATE) // Ensure files are not public
                .build();

        s3Client.putObject(putRequest, RequestBody.fromFile(file));

        // Return the CloudFront URL instead of the S3 URL
        return cloudFrontUrl + "/" + key;
    }
}
```

---

## **4. Modify `application.yml` to Include CloudFront URL**
```yaml
aws:
  s3:
    bucket-name: my-image-bucket
  region: us-east-1
  cloudfront:
    url: https://d123abc.cloudfront.net
```

---

## **5. Secure Image Access with Signed URLs (Optional)**
If images **shouldn’t be publicly accessible**, we’ll generate **temporary signed URLs**.

### **5.1 Create CloudFront Private Key**
Run:
```sh
openssl genrsa -out private_key.pem 2048
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

Upload `public_key.pem` to AWS **CloudFront → Public Key Settings**.

---

### **5.2 Generate Signed URLs in Spring Boot**
```java
package com.example.imageprocessing.service;

import com.example.imageprocessing.model.ImageMetadata;
import com.example.imageprocessing.repository.ImageMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;

import java.time.Instant;
import java.util.Base64;

@Service
public class CloudFrontSignedUrlService {

    private final CloudFrontClient cloudFrontClient;
    private final String cloudFrontUrl;
    private final String privateKey;
    private final String keyPairId;

    public CloudFrontSignedUrlService(@Value("${aws.cloudfront.url}") String cloudFrontUrl,
                                      @Value("${aws.cloudfront.private-key}") String privateKey,
                                      @Value("${aws.cloudfront.key-pair-id}") String keyPairId) {
        this.cloudFrontClient = CloudFrontClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        this.cloudFrontUrl = cloudFrontUrl;
        this.privateKey = privateKey;
        this.keyPairId = keyPairId;
    }

    public String generateSignedUrl(String filePath, int expirySeconds) throws Exception {
        Instant expiration = Instant.now().plusSeconds(expirySeconds);
        String policy = String.format(
                "{\"Statement\":[{\"Resource\":\"%s/%s\",\"Condition\":{\"DateLessThan\":{\"AWS:EpochTime\":%d}}}]}",
                cloudFrontUrl, filePath, expiration.getEpochSecond()
        );

        byte[] signedPolicy = signPolicy(policy);
        String signature = Base64.getEncoder().encodeToString(signedPolicy);

        return String.format("%s/%s?Expires=%d&Signature=%s&Key-Pair-Id=%s",
                cloudFrontUrl, filePath, expiration.getEpochSecond(), signature, keyPairId);
    }

    private byte[] signPolicy(String policy) throws Exception {
        java.security.Signature signer = java.security.Signature.getInstance("SHA256withRSA");
        signer.initSign(java.security.KeyFactory.getInstance("RSA")
                .generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(
                        Base64.getDecoder().decode(privateKey))));
        signer.update(policy.getBytes());
        return signer.sign();
    }
}
```

---

## **6. Modify Controller to Return Signed URLs**
```java
@RestController
@RequestMapping("/api")
public class ImageProcessingController {

    private final ImageMetadataRepository imageRepository;
    private final CloudFrontSignedUrlService signedUrlService;

    public ImageProcessingController(ImageMetadataRepository imageRepository,
                                     CloudFrontSignedUrlService signedUrlService) {
        this.imageRepository = imageRepository;
        this.signedUrlService = signedUrlService;
    }

    @GetMapping("/images/{id}")
    public String getSignedImageUrl(@PathVariable Long id) throws Exception {
        ImageMetadata metadata = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        return signedUrlService.generateSignedUrl(metadata.getFilename(), 600); // 10-minute expiry
    }
}
```

---

## **7. Testing the CDN API**
### **Convert & Upload an Image**
```sh
curl -X POST -F "file=@input.jpg" http://localhost:8080/api/convert-ycbcr
```
Response:
```json
Processed image stored at: https://d123abc.cloudfront.net/processed/input.jpg
```

### **Retrieve a Signed URL**
```sh
curl -X GET http://localhost:8080/api/images/1
```
Response:
```json
"https://d123abc.cloudfront.net/processed/input.jpg?Expires=1712345678&Signature=abc123&Key-Pair-Id=APK12345"
```

---

## **8. Final Thoughts**
✅ **CloudFront delivers images faster** with global caching.  
✅ **Signed URLs** ensure only authorized users access images.  
✅ **AWS S3 + CloudFront** is cost-effective for large-scale applications.
