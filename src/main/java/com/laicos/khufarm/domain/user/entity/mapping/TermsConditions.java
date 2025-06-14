package com.laicos.khufarm.domain.user.entity.mapping;

import com.laicos.khufarm.domain.user.enums.TermsEssential;
import com.laicos.khufarm.domain.user.enums.converter.TermsEssentialConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "termsConditions")
public class TermsConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 1000)
    private String content;

    @Convert(converter = TermsEssentialConverter.class)
    @Column(nullable = false)
    private TermsEssential termsEssential;
}
