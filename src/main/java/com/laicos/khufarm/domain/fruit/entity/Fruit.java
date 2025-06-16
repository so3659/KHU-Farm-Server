package com.laicos.khufarm.domain.fruit.entity;

import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.user.entity.mapping.Seller;
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
    private String imageUrl;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private String deliveryCompany;

    @Column(nullable = false)
    private Integer deliveryDay;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer ratingSum;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer ratingCount;

    @Column(nullable = false)
    private String description;

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
}
