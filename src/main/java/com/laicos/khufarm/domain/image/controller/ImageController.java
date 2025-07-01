package com.laicos.khufarm.domain.image.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.image.service.ImageCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Validated
@Tag(name = "Image", description = "사진 관련 API")
public class ImageController {

    private final ImageCommandService imageCommandService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<String> saveImage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("image")MultipartFile imageFile
    ) throws IOException {
        String imagePath = imageCommandService.saveImage(customUserDetails.getUser(), imageFile);
        return BaseResponse.onSuccess(imagePath);
    }
}
