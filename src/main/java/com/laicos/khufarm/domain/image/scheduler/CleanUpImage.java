package com.laicos.khufarm.domain.image.scheduler;

import com.amazonaws.services.s3.AmazonS3;
import com.laicos.khufarm.domain.image.entity.Image;
import com.laicos.khufarm.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class CleanUpImage {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.ncp.s3.bucket}")
    private String bucket;

    @Scheduled(cron = "0 0 * * * *") // 매 시간마다
    public void cleanUpTempImages() {
        LocalDateTime expired = LocalDateTime.now().minusHours(24);
        List<Image> tempImages = imageRepository.findByImageStatusAndCreatedAtBefore("TEMP", expired);
        for (Image img : tempImages) {
            amazonS3.deleteObject(bucket, img.getImagePath());
            imageRepository.delete(img);
        }
    }

}
