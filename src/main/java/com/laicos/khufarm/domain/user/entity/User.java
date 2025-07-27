package com.laicos.khufarm.domain.user.entity;

import com.laicos.khufarm.domain.user.enums.UserStatus;
import com.laicos.khufarm.domain.user.enums.UserType;
import com.laicos.khufarm.domain.user.enums.converter.UserStatusConverter;
import com.laicos.khufarm.domain.user.enums.converter.UserTypeConverter;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Convert(converter = UserStatusConverter.class)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Column(nullable = false)
    private double version;

    @Convert(converter = UserTypeConverter.class)
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer totalPoint;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer totalDonation;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer totalPrice;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer totalWeight;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer discountedPrice;

    private String fcmToken;

    public void setEncodedPassword(String password) {
        this.password = password;
    }

    public void updateTotalWeight(Integer weight) {
        this.totalWeight += weight;
    }

    public void updateTotalPrice(Integer price) {
        this.totalPrice += price;
    }

    public void updateDiscountedPrice(Integer price) {
        this.discountedPrice += price;
    }

    public void updateTotalPoint(Integer point) {
        this.totalPoint += point;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
