package com.laicos.khufarm.domain.image.service;

import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageCommandService {

    String saveImage(User user, MultipartFile image) throws IOException;
}
