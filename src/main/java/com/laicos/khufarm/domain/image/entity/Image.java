package com.laicos.khufarm.domain.image.entity;

import com.laicos.khufarm.domain.image.enums.ImageStatus;
import com.laicos.khufarm.domain.image.enums.converter.ImageStatusConverter;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "image")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String imagePath;

    @Convert(converter = ImageStatusConverter.class)
    @Column(nullable = false)
    private ImageStatus imageStatus;

    public void updateImageStatus(ImageStatus imageStatus) {
        this.imageStatus = imageStatus;
    }
}

