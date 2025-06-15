package com.laicos.khufarm.domain.user.entity.mapping;

import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "agreement")
public class Agreement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="termsConditions_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TermsConditions termsConditions;
}
