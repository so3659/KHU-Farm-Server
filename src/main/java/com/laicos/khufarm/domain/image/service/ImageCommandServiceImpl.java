package com.laicos.khufarm.domain.image.service;

import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class ImageCommandServiceImpl implements ImageCommandService{

    @Value("${file.dir}")
    private String uploadDir; // 파일이 저장될 디렉토리 경로

    @Override
    public String saveImage(User user, MultipartFile image) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        // 실제 파일이 저장될 경로
        String filePath = uploadDir + user.getUserId() + fileName;
        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/images/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;
    }
}
