package com.laicos.khufarm.domain.inquiry.entity;

import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "inquiryReply")
public class InquiryReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inquiry_id", nullable = false)
    private Inquiry inquiry;

    protected void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
