package com.laicos.khufarm.domain.image.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.laicos.khufarm.domain.image.converter.ImageConverter;
import com.laicos.khufarm.domain.image.entity.Image;
import com.laicos.khufarm.domain.image.repository.ImageRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService{

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.ncp.s3.bucket}")
    private String bucket;

    @Override
    public String saveImage(User user, MultipartFile image) throws IOException {

        String originalFilename = image.getOriginalFilename();
        String storedFileName = createStoredFileName(user.getUserId(), originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        try {
            PutObjectRequest request = new PutObjectRequest(
                    bucket,
                    storedFileName,
                    image.getInputStream(),
                    metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(request);
        } catch (
        AmazonS3Exception e) {
            throw new IOException("S3에 파일 업로드 중 오류 발생: " + e.getMessage(), e);
        } catch(SdkClientException e) {
            throw new IOException("S3 클라이언트 오류: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("파일 스트림 처리 중 오류 발생: " + e.getMessage(), e);
        }

        String imageUrl = amazonS3.getUrl(bucket, storedFileName).toString();

        Image imageEntity = ImageConverter.toImage(imageUrl, storedFileName);

        imageRepository.save(imageEntity);

        return imageUrl;
    }

    /**
     * Object Storage 내에서 파일을 관리하기 쉽도록 경로를 포함한 파일 이름을 생성합니다.
     * 예: images/{userId}/{uuid}_{originalFilename}
     */
    private String createStoredFileName(String userId, String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return "images/" + userId + "/" + uuid + "_" + originalFilename;
    }
}