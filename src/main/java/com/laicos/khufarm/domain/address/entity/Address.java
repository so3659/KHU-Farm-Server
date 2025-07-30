package com.laicos.khufarm.domain.address.entity;

import com.laicos.khufarm.domain.address.dto.request.AddressCreateRequest;
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
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String portCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private boolean isDefault;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void updateAddress(AddressCreateRequest addressCreateRequest) {
        this.addressName = addressCreateRequest.getAddressName();
        this.portCode = addressCreateRequest.getPortCode();
        this.address = addressCreateRequest.getAddress();
        this.detailAddress = addressCreateRequest.getDetailAddress();
        this.isDefault = addressCreateRequest.isDefaultAddress();
        this.recipient = addressCreateRequest.getRecipient();
        this.phoneNumber = addressCreateRequest.getPhoneNumber();
    }
}
