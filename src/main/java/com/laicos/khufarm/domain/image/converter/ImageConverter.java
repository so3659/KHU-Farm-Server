package com.laicos.khufarm.domain.image.converter;

import com.laicos.khufarm.domain.image.entity.Image;
import com.laicos.khufarm.domain.image.enums.ImageStatus;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {

    public static Image toImage(String imageUrl, String imagePath) {
        return Image.builder()
                .imagePath(imagePath)
                .imageUrl(imageUrl) // Assuming imageUrl is the same as imagePath
                .imageStatus(ImageStatus.TEMP)
                .build();
    }
}
