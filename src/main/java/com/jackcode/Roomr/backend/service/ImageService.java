package com.jackcode.Roomr.backend.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
    private final AWSCredentials CREDENTIALS = new BasicAWSCredentials(
            "AKIAIK5MJDBNUDUGAXVQ",
            "oIP+3sRURNRYTPPRN+WVJp3x8MCAy8ZIAAIpdq/t"
    );
    private final String BUCKET_NAME = "elasticbeanstalk-us-east-2-537812688195";
    private final AmazonS3 s3;

    private Map<String, List<Image>> images = new HashMap<>();

    public ImageService() {
        // Connect to s3Client
        s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS))
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
        return s3.generatePresignedUrl(new GeneratePresignedUrlRequest(
                BUCKET_NAME, key).withMethod(HttpMethod.GET)).toString();
    }

    private String getRoomNumberFromKey(String key) {
        String[] partsOfKey = key.split("/");
        return partsOfKey[2];
    }

    public List<Image> getImagesForRoom(String roomNumber) {
        return images.getOrDefault(roomNumber, new ArrayList<Image>());
    }


}
