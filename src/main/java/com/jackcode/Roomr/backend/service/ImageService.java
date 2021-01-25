package com.jackcode.Roomr.backend.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.vaadin.flow.component.html.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageService {
    // Add in dev mode
//    private final AWSCredentials CREDENTIALS = new BasicAWSCredentials(
//            System.getenv("s3AccessKey"),
//            System.getenv("s3SecretKey")
//    );
    private final String BUCKET_NAME = "elasticbeanstalk-us-east-2-537812688195";
    private final AmazonS3 s3;

    private Map<String, List<Image>> images = new HashMap<>();

    public ImageService() {
        // Connect to s3Client
        s3 = AmazonS3ClientBuilder
                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS))
                .withRegion(Regions.US_EAST_2)
                .build();

        // Check if bucket exists
        if (s3.doesBucketExistV2(BUCKET_NAME)) {
            ListObjectsV2Request request =
                    new ListObjectsV2Request()
                            .withBucketName(BUCKET_NAME)
                            .withPrefix("resources/images/");
            ListObjectsV2Result result = s3.listObjectsV2(request);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                if(objectSummary.getKey().contains(".jpg")) {
                    addImageToMap(objectSummary.getKey());
                }
            }
        }
    }

    private void addImageToMap(String key) {
        String roomNumber = getRoomNumberFromKey(key);
        String imageURL = getUrlAsString(key);
        Image image = new Image(
                imageURL,
                "Error loading photo");

        if (images.containsKey(roomNumber)) {
            images.get(roomNumber).add(image);
        } else {
            List<Image> newList = new ArrayList<>();
            newList.add(image);
            images.put(roomNumber, newList);
        }
    }

    private String getUrlAsString(String key) {
        // Set the presigned URL to expire after one week.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 24 * 7;
        expiration.setTime(expTimeMillis);


        return s3.generatePresignedUrl(new GeneratePresignedUrlRequest(
                BUCKET_NAME, key).withMethod(HttpMethod.GET).withExpiration(expiration)).toString();
    }

    private String getRoomNumberFromKey(String key) {
        String[] partsOfKey = key.split("/");
        return partsOfKey[2];
    }

    public List<Image> getImagesForRoom(String roomNumber) {
        return images.getOrDefault(roomNumber, new ArrayList<Image>());
    }


}
