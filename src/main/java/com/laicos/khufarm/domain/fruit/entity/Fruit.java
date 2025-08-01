package com.laicos.khufarm.domain.fruit.entity;

import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.delivery.enums.converter.DeliveryCompanyConverter;
import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.fruit.enums.FruitStatus;
import com.laicos.khufarm.domain.fruit.enums.converter.FruitStatusConverter;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "fruit")
public class Fruit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String widthImageUrl;

    @Column(nullable = false)
    private String squareImageUrl;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    @Convert(converter = DeliveryCompanyConverter.class)
    private DeliveryCompany deliveryCompany;

    @Column(nullable = false)
    private Integer deliveryDay;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer ratingSum;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer ratingCount;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer stock;

    @Convert(converter = FruitStatusConverter.class)
    @Column(nullable = false)
    private FruitStatus fruitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id", nullable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fruitCategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FruitCategory fruitCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="wholesaleRetailCategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WholesaleRetailCategory wholesaleRetailCategory;

    public void decreaseFruitStock(Integer count) {
        this.stock -= count;
    }

    public void updateRating(Integer rating) {
        this.ratingSum += rating;
        this.ratingCount++;
    }

    public void updateFruit(FruitCategory fruitCategory, WholesaleRetailCategory wholesaleRetailCategory, FruitAddRequest fruitAddRequest) {
        this.fruitCategory = fruitCategory;
        this.wholesaleRetailCategory = wholesaleRetailCategory;
        this.title = fruitAddRequest.getTitle();
        this.widthImageUrl = fruitAddRequest.getWidthImage();
        this.squareImageUrl = fruitAddRequest.getSquareImage();
        this.price = fruitAddRequest.getPrice();
        this.weight = fruitAddRequest.getWeight();
        this.deliveryCompany = DeliveryCompany.fromName(fruitAddRequest.getDeliveryCompany());
        this.deliveryDay = fruitAddRequest.getDeliveryDay();
        this.description = fruitAddRequest.getDescription();
        this.stock = fruitAddRequest.getStock();
    }
}
